const userService = require('../services/User');

const createUser = async (req, res) => {
  const user = await userService.getUser(req.body.username);
  if (user) {
    return res.status(409).json({message: 'Username already exist in the system'});
  }
  const {username, password, displayName, profilePic} = req.body;
  const savedUser = await userService.createUser(
      username, password, displayName, profilePic
  );
  if (savedUser) {
    return res.status(200).json(savedUser);
  } else {
    return res.status(409).json("error occurred while register a new user");
  }
};

const getUser = async (req, res) => {
  const user = await userService.getUser(req.params.username);
  if (user) {
    res.status(200).json({username: user.username, displayName: user.displayName, profilePic: user.profilePic});
  } else {
    res.status(404).send("User not found");
  }
}

module.exports = {createUser, getUser};