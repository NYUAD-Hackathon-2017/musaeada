'use strict'

const dbHelper = require('./db.js');
const app = require('express')();
const env = require('node-env-file');
const assert = require('assert');

env(__dirname + '/.env');

const TWILIO_NUMBER = process.env.TWILIO_NUMBER;
const user = process.env.MONGO_USER;
const pass = process.env.MONGO_PW;
const host = process.env.MONGO_HOST;
const port = process.env.MONGO_PORT;

var http = require('http');
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true }));


// DB Connection stuff
var MongoClient = require('mongodb').MongoClient;
var ObjectId = require('mongodb').ObjectID;
var twilio = require('twilio');

var accountSid = process.env.TWILIO_ACCOUNT_SID; // Your Account SID from www.twilio.com/console
var authToken = process.env.TWILIO_AUTH_TOKEN;   // Your Auth Token from www.twilio.com/console


var twilio = require('twilio');
var client = new twilio.RestClient(accountSid, authToken);


function sendMessageTo(sender, receiver, message) {
  console.log("Message sent.")
  client.messages.create({
      body: sender + ": " + message,
      to: receiver,  // Text this number
      from: process.env.TWILIO_PHONE_NUMBER // From a valid Twilio number
  }, function(err, message) {
     if(err) {
        console.error(err.message);
     }
  });

}

// Set up mongo client.
let url = `mongodb://${user}:${pass}@${host}:${port}`;
if (process.env.MONGO_DB) {
  url = `${url}/${process.env.MONGO_DB}`;
}
console.log('url is ' + url);

// On initialization, store associations
function storeEdges(sender, subscribers) {

}

function add_data(user_number, name, subscriber) {
  console.log("Adding data: " + user_number + ", " + name + ", " + subscriber)
	MongoClient.connect(url, function(err, db) {

		var opts = {
			broadcaster: user_number,
      broadcastername: name,
			subscriber: subscriber,
		};

		dbHelper.createNewEdge(db, opts, function() {
	    db.close();
	  });
	});
}

function broadcast(sender_number, msg) {
	// retrieve associations w this sender & send to all
  MongoClient.connect(url, function(err, db) {

		var opts = {
			broadcaster: sender_number,
		};

	  dbHelper.retrieveEdges(db, opts, function(mappings_of_user) {
	  	console.log(mappings_of_user);

      for(var i=0; i < mappings_of_user.length; i++){
        var subscriber_number = mappings_of_user[i]['subscriber'];
        var sender_name = mappings_of_user[i]['broadcastername']
        console.log(sender_name + " has subscriber: " + subscriber_number);
        sendMessageTo(sender_name, subscriber_number, msg);
      }
	  	db.close();
	  });
	});
}


function receive(sender_number, msg) {
  if (msg[0] == '+') { // replace with check for sign up
    console.log("sign me up!")
  } else {
    broadcast(sender_number, msg);
  }
}


// --------------------------- ENDPOINTS ------------------------------

app.get('/', function (req, res) {
  add_data(process.env.NAOMI_NUMBER, "Naomi", process.env.RASHIQ_NUMBER);
  // broadcast(process.env.NAOMI_NUMBER, "Message");

  res.send('Sending message!')
})

app.post('/store', function(sReq, sRes){
  console.log('receiving request ' + sReq);
})

app.post('/receive', function(req, res){
    console.log("Received: " + req.body.Body + " from " + req.body.From);
    receive(req.body.From, req.body.Body);
})

app.get('/send', function(sReq, sRes){
  // sendMessageTo("Naomi", process.env.RASHIQ_NUMBER, "Such a Rashiq thing to do...");
  console.log('Receiving request ' + sReq);
})

app.listen(3000, function () {
	//db_test();
  console.log('listening on port 3000!')
})
