const jwt = require('jsonwebtoken');
const {key: key} = require('../models/Token');

function createToken(username) {
  return jwt.sign({username}, key);
}

module.exports = {createToken};