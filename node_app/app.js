'use strict'

const dbHelper = require('./db.js');
const utils = require('./utils.js')
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

function addData(user_number, name, subscriber) {
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

function sendTo(receiver, msg) {}

function batchAdd(cleaned, sender, name) {
	var batch = [];
	cleaned.forEach(function(num) {
		batch.push({
			broadcaster: sender,
			broadcastername: name,
			subscriber: num,
		});
	});

	MongoClient.connect(url, function(err, db) {
		dbHelper.createManyEdges(db, batch, function() {
			db.close();
		});
	});
}

// Determines if we have an ADD or SEND upon first receipt
function parseMessage(sender, msg) {
	var action = msg.split(' ')[0];

  if (action === 'ADD' || action === 'add') {
  	// Get the name for the current sender's number.
  	var opts = {
  		number : sender,
  	};

  	// Query for the sender's name.
  	MongoClient.connect(url, function(err, db) {
	  	dbHelper.retrieveName(db, opts, function(obj) {
	  		//console.log("name is " + obj["name"]);
	  		var name = obj['name'];
	  		// Get the numbers and batch add async to db
		  	var cleanedNumbers = utils.getNumbers(msg);
		  	//console.log(cleanedNumbers + " cleaned numbers");
		  	batchAdd(cleanedNumbers, sender, name);
		  	db.close();
	  	});
	  });
  } else if (action === 'SEND' || action === 'send') {
  	// Get the message to send.
  	var cleanedMessage = utils.getMessage(msg);

  	// Broadcast the cleaned message.
  	broadcast(sender, cleanedMessage);
  } else if (action === 'SELF' || action === 'self') {
  	var name = utils.getMessage(msg);

  	var opts = {
  		number: sender,
  		name: name,
  	};

  	// Store the name.
  	MongoClient.connect(url, function(err, db) {
	  	dbHelper.createNewName(db, opts, function() {
	  		db.close();
	  	});
	  });
  } else {
  	console.log('No associated action with message ' + msg);
  }
}

// DON'T run this often. it sends lots of messages and that costs $$$.
function parseTest() {
	// Test the name storage.
	var nameMessage = "SELF Shirley";
	//parseMessage(nameMessage, process.env.SHIRLEY_NUMBER);

	var addMessage = "ADD " + process.env.NAOMI_NUMBER + ", " + process.env.RASHIQ_NUMBER;
	parseMessage(addMessage, process.env.SHIRLEY_NUMBER);

	var sendMessage = "SEND hello to everyone!";
	parseMessage(sendMessage, process.env.SHIRLEY_NUMBER);
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
    parseMessage(req.body.From, req.body.Body);
})

app.get('/send', function(sReq, sRes){
  // sendMessageTo("Naomi", process.env.RASHIQ_NUMBER, "Such a Rashiq thing to do...");
  console.log('Receiving request ' + sReq);
})

app.listen(3000, function () {
	//parseTest();
  console.log('listening on port 3000!')
})
