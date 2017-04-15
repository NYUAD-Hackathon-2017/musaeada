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

			if (cleaned.length > 11 || cleaned.length < 10) {
				console.log('Invalid phone number found ' + cleaned);
			} else {
				cleanedNumbers.push(cleaned);
			}

		});

		return cleanedNumbers;
  },

  getSubscribeNumber : function(msg) {
  	var string = msg.substr(msg.indexOf(" ") + 1);

  	var cleaned = string.replace(/\D/g, "");

  	if (cleaned.length > 11 || cleaned.length < 10) {
  		console.log('Invalid phone number in subscribe number ' + cleaned);
  		return '';
  	} 
  	return cleaned;
  }


};
