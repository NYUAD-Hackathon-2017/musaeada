'use strict'

const dbHelper = require('./db.js');
const app = require('express')();
const env = require('node-env-file');
const assert = require('assert');

// DB Connection stuff
var MongoClient = require('mongodb').MongoClient;
var ObjectId = require('mongodb').ObjectID;

env(__dirname + '/.env');

const TWILIO_NUMBER = process.env.TWILIO_NUMBER;
const user = process.env.MONGO_USER;
const pass = process.env.MONGO_PW;
const host = process.env.MONGO_HOST;
const port = process.env.MONGO_PORT;

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
