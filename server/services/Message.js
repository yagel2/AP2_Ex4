const Chats = require("../models/Chats");
const Message = require('../models/Message');

const createMessage = async (user, content) => {
  const userWithoutPassword = {username: user.username, displayName: user.displayName, profilePic: user.profilePic};
  return new Message({
    sender: userWithoutPassword,
    content: content
  });
};

const getMessages = async (id) => {
  try {
    return await Chats.findOne({ _id: id })
        .populate('messages');
  } catch (error) {
    console.log("Error in finding chats: " + error);
    return null;
  }
}
const notifySendMessage = (io, receiverSocketID, message) => {
  io.to(receiverSocketID).emit('receive_message', message);
};

module.exports = {createMessage, getMessages, notifySendMessage}
