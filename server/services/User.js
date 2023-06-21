const User = require("../models/User")

const createUser = async (username, password, displayName, profilePic) => {
  try {
    const user = new User({
      username, password, displayName, profilePic,
    });
    return await user.save();
  } catch (error) {
    console.log("error while saving new user")
    throw error;
  }
};

const getUser = async (username) => {
  return User.findOne({username});
}

const attachSocket = (username, socketId) => {
  return User.findOneAndUpdate({ username }, { socketId }, { new: true });
};

const getSocketID = (username) => {
  return User.findOne({ username }).then(user => {
    return user ? user.socketId : null;
  });
};


const disconnect = (username) => {
  return User.findOneAndUpdate({ username }, { socketId: null }, { new: true });
};

module.exports = {
  attachSocket,
  getSocketID,
  disconnect,createUser, getUser
};