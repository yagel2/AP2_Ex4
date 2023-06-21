const userServices = require('../services/User');
const chatsServices = require('../services/Chats');

const createChat = async (req, res) => {
  const user = await userServices.getUser(req.username);
  const contact = await userServices.getUser(req.body.username);
  if (!contact) {
    return res.status(400).send("Username doesn't exist");
  }
  if (user.username === contact.username) {
    return res.status(400).send('You are not allowed to add yourself');
  }
  const chatId = await chatsServices.createChat(user, contact);
  if (!chatId) {
    return res.status(500).send('Error occurred while creating a new chat');
  }
  return res.status(200).json({
    id: chatId,
    user: {username: contact.username, displayName: contact.displayName, profilePic: contact.profilePic}
  })
};

const getChats = async (req, res) => {
  const chats = await chatsServices.findChatByUser(req.username);
  return res.status(200).json(chats)
}

const getChatsByID = async (req, res) => {
  const chats = await chatsServices.getChatsByID(req.params.id);
  if (!chats) {
    return res.status(404).send("Chat not found");
  }
  return res.status(200).json(chats);
};

const deleteChat = async (req, res) => {
  const deleteResult = await chatsServices.deleteChat(req.params.id);
  if (deleteResult.deletedCount === 1) {
    return res.status(200)
  } else {
    return res.status(400)
  }
}

module.exports = {createChat, getChats, getChatsByID, deleteChat}