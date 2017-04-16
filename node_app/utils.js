'use strict'
var assert = require('assert');

module.exports = {
  // Parse message
  getAction : function(msg) {

  },

  getMessage : function(msg) {
  	// get rid of the SEND
  	return msg.substr(msg.indexOf(" ") + 1);
  },

  getNumbers : function(msg) {
		var numbersString = msg.substr(msg.indexOf(" ") + 1);

		// Make it an array and format each number.
		var uncleaned = numbersString.split(',');
		var cleanedNumbers = [];
		uncleaned.forEach(function(num) {
			var cleaned = num.replace(/\D/g, "");

			/*if (cleaned.length > 11 || cleaned.length < 10) {
				console.log('Invalid phone number found ' + cleaned);
			} else {
				cleanedNumbers.push(cleaned);
			}*/
			cleanedNumbers.push(cleaned);

		});

		return cleanedNumbers;
  },

  getSubscribeNumber : function(msg) {
  	var string = msg.substr(msg.indexOf(" ") + 1);

  	var cleaned = string.replace(/\D/g, "");

  	/*if (cleaned.length > 11 || cleaned.length < 10) {
  		console.log('Invalid phone number in subscribe number ' + cleaned);
  		return '';
  	}*/

    cleaned = '+' + cleaned;

  	return cleaned;
  }
  

};

// right outer join

// ping whether sms went through
// HELP messages get propagated to emergency services
// IFTTT - post on facebook
// sustainability
// updating them about their network

// bidirectional associations
// where are we getting the funding.
  // government
  // non profit
  // low cost
  // highlight the no-data
  // has a fallback!!!
