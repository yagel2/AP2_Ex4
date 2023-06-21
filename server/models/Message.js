const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const Users = new Schema({
  username: {
    type: String,
    required: true,
  },
  displayName: {
    type: String,
    required: true
  },
  profilePic: {
    type: String,
    required: false
  }
});

const Message = new Schema({
  created: {
    type: Date,
    default: Date.now
  },
  sender: {
    type : Users
  },
  content: {
    type: String,
    default: ""
  }
});

module.exports = mongoose.model('Message', Message);