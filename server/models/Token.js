const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const key = "YagelAndHadarSecretKey";

const Token = new Schema({
  username: {
    type: String,
    required: true
  },
  password: {
    type: String,
    required: true
  }
});

module.exports = {
  key: key,
  Token: mongoose.model('Token', Token)
};