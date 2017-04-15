'use strict'

var assert = require('assert');

module.exports = {
  // Use the insertOne method to create a new document when a
  // new edge is created
  createNewEdge : function(db, ops, callback) {
    db.collection('edges').update( {
      "broadcaster": ops.broadcaster,
      //"broadcastername": ops.broadcastername,
      //"codename": ops.name,
      "subscriber": ops.subscriber,
    }, function(err, result) {
      assert.equal(err, null);
      callback();
    });
  },

  createManyEdges : function(db, entries, sender, callback) {
    //console.log(entries);
    // Deduplicate first by calling retrieveEdges. 
    this.retrieveEdges(db, {broadcaster: sender}, function(results) {
      // Map entries to just numbers. 
      var numbers = results.map(function(result) {
        return result['subscriber'];
      });

      var deduped = entries.filter(function(entry) {
        return numbers.indexOf(entry.subscriber) === -1;
      });

      if (deduped.length === 0) {
        callback();
        return;
      }

      db.collection('edges').insert(deduped, function(err, result) {
        assert.equal(err, null);
        callback();
      });
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
