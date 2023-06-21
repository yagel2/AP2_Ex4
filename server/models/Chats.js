const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Message = require("./Message");

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
    required: true
  }
});

const Chats = new Schema({
  users: {
    type: [Users],
    required: true
  },
  messages: {
    type: [Message.schema],
  }
});

module.exports = mongoose.model('Chats', Chats);