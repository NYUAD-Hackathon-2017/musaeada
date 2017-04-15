'use strict'

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

  createManyEdges : function(db, entries, callback) {
    console.log(entries);
    db.collection('edges').insert(entries, function(err, result) {
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
      callback(doc);
    });
  },

  // Add a name number association 
  createNewName : function(db, ops, callback) {
    db.collection('names').insertOne({
      "number" : ops.number,
      "name" : ops.name,
    }, function(err, result) {
      assert.equal(err, null);
      callback();
    });
  },

  // Retrieve a name number association 
  retrieveName : function(db, ops, callback) {
    var cursor = db.collection('names').find(ops);
    cursor.toArray(function(err, doc) {
      assert.equal(err, null);
      assert.notEqual(doc, [], 'Found no entries');
      callback(doc[0]);
    });
  }
};
