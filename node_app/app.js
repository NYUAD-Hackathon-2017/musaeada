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


// DB Connection stuff
var MongoClient = require('mongodb').MongoClient;
var ObjectId = require('mongodb').ObjectID;
var twilio = require('twilio');

var accountSid = process.env.TWILIO_ACCOUNT_SID; // Your Account SID from www.twilio.com/console
var authToken = process.env.TWILIO_AUTH_TOKEN;   // Your Auth Token from www.twilio.com/console


var twilio = require('twilio');
var client = new twilio.RestClient(accountSid, authToken);

client.messages.create({
    body: 'Hello from Node',
    to: process.env.RASHIQ_NUMBER,  // Text this number
    from: process.env.TWILIO_PHONE_NUMBER // From a valid Twilio number
}, function(err, message) {
   if(err) {
      console.error(err.message);
   }
});


// On initialization, store associations
function storeEdges(sender, subscribers) {

}

function broadcast(sender, msg) {
	// retrieve associations w this sender
	// send message to each number (sendTo)
}


function receive(sender, receiver, msg) {

}

function sendTo(receiver, msg) {

}

app.get('/', function (req, res) {
  res.send('Hello World!')
})

app.post('/store', function(sReq, sRes){
  console.log('receiving request ' + sReq);
})

app.get('/receive', function(sReq, sRes){
  console.log('receiving request ' + sReq);
})

app.listen(3000, function () {
  console.log('listening on port 3000!')
})
