var assert = require('assert');

module.exports = {
  // Use the insertOne method to create a new document when a new incident report is started
  createNewEdge : function(db, ops, callback) {
    db.collection('edges').insertOne( {
      "broadcaster": ops.broadcaster,
      "timestamp": ops.ts,
      "codename": ops.name,
      "subscriber": ops.subscriber,
    }, function(err, result) {
      assert.equal(err, null);
      callback();
    });
  },

  retrieveEdge : function(db, ops, callback) {
    
  }

};