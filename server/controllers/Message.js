const userService = require('../services/User');
const chatService = require('../services/Chats');
const messageService = require('../services/message');
const firebaseService = require('../services/Firebase');

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

  const receiver = await chatService.findReceiver(req.username, chatID);
  await notifyReceiver(receiver, req.username, message);
  return res.status(200).json(message);
}

const getMessages = async (req, res) => {
  const chat = await messageService.getMessages(req.params.id);
  if (!chat) {
    return res.status(404).send("Chat not found");
  }
  return res.status(200).json(chat.messages);
}

const notifyReceiver = async (receiver, username, message) => {
  const token = await firebaseService.getFirebaseToken(receiver._id);
  if (token) {
    console.log('sending ' + message.content + ', to ' + token + ' (' + receiver.username + ')');
    await firebaseService.notify(token, {
      sender: username,
      created: message.created,
      content: message.content
    });
  }
}

module.exports = {createMessage, getMessages};
