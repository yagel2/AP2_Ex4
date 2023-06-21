const jwt = require("jsonwebtoken");
const {key: key} = require('../models/Token');
const userService = require('../services/User');
const tokenService = require('../services/Token');

async function login(req, res) {
  const {username, password} = req.body;
  const user = await userService.getUser(username);
  if (!user || user.password !== password) {
    return res.status(404).json({message: 'Invalid username and/or password'});
  }
  const token = tokenService.createToken(username);
  return res.status(200).json(token);
}

const verifyToken = (req, res, next) => {
  if (req.headers.authorization) {
    const token = req.headers.authorization.split(" ")[1];
    try {
      req.username = jwt.verify(token, key).username;
      return next()
    } catch (err) {
      return res.status(401).send("Invalid Token");
    }
  } else {
    return res.status(403).send('Token required');
  }
};

module.exports = {login, verifyToken};