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
		console.log(numbersString);
		var uncleaned = numbersString.split(',');
		console.log(uncleaned);
		var cleanedNumbers = [];
		uncleaned.forEach(function(num) {
			console.log(num);
			var cleaned = num.replace(/\D/g, "");

			if (cleaned.length > 11 || cleaned.length < 10) {
				console.log('Invalid phone number found ' + cleaned);
			} else {
				cleanedNumbers.push(cleaned);
			}

		});

		return cleanedNumbers;
  }

};
