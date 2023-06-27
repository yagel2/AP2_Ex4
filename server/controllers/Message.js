const userService = require('../services/User');
const chatService = require('../services/Chats');
const messageService = require('../services/message');

const createMessage = async (req, res) => {
  const user = (await userService.getUser(req.username));
  const chatID = req.params.id;
  const message = await messageService.createMessage(user, req.body.msg);
  if (!message) {
    return res.status(500).send("Error creating a new message.");
  }
  if (!(await chatService.addMessage(chatID, message))) {
    return res.status(500).send("Error adding message to chat.");
  }
  return res.status(200).json(message);
}

const getMessages = async (req, res) => {
  const chat = await messageService.getMessages(req.params.id);
  if (!chat) {
    return res.status(404).send("Chat not found");
  }
  return res.status(200).json(chat.messages);
}

module.exports = {createMessage, getMessages};
