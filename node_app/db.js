var assert = require('assert');

module.exports = {
  // Use the insertOne method to create a new document when a
  // new edge is created
  createNewEdge : function(db, ops, callback) {
    db.collection('edges').insertOne( {
      "broadcaster": ops.broadcaster,
      "broadcastername": ops.broadcastername,
      //"codename": ops.name,
      "subscriber": ops.subscriber,
    }, function(err, result) {
      assert.equal(err, null);
      callback();
    });
  },

  // Use this to retrieve a list of edges from the mongo db
  retrieveEdges : function(db, ops, callback) {
    //var edgeList = [];
    var cursor = db.collection('edges').find(ops);

    cursor.toArray(function(err, doc) {
      assert.equal(err, null);
      assert.notEqual(doc, [], 'Found no entries.');
      for (var i in doc) {
        thisText = doc[i];
        //edgeList.unshift(thisText);
      }
      callback(doc);
    });
  }

};
