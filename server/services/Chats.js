const User = require('../models/User');
const Chats = require('../models/Chats');

const createChat = async (user, contact) => {
  try {
    const chat = new Chats({
      users: [user, contact]
    });
    await chat.save();
    return chat._id;
  } catch (error) {
    console.log('Error occurred while creating a new chat: ' + error);
    return null;
  }
};

const addMessage = async (id, message) => {
  try {
    const chat = await findChatById(id);
    await chat.updateOne(
        {
          $push: {
            messages: {
              $each: [message],
              $position: 0
            }
          }
        }
    );
    await chat.save();
    return 'success';
  } catch (error) {
    console.log("Error in adding message to chat: " + error);
    return null;
  }
}

const findChatById = async (id) => {
  return Chats.findOne({_id: id});
}

const findChatByUser = async (username) => {
  const user = await User.findOne({username});
  if (!user) {
    return null;
  }
  const chats = await Chats.find({
    users: {$elemMatch: {username: user.username}}
  }).populate('users').populate('messages');
  return chats.map(chat => {
    const contact = chat.users.find(tempUser => tempUser.username !== username);
    return {
      id: chat.id,
      user: contact,
      lastMessage: chat.messages[0] || null
    }
  })
}

const getChatsByID = async (id) => {
  try {
    return await Chats.findOne({_id: id})
        .populate('users')
        .populate('messages');
  } catch (error) {
    console.log("Error in finding chats: " + error);
    return null;
  }
}

const deleteChat = async (id) => {
  return Chats.findOneAndDelete({_id: id});
}

const findReceiver = async (currentUsername, chatId) => {
  const chat = await Chats.findById(chatId).populate('users');
  const receiver = chat.users.find(user => user.username !== currentUsername);

  if (!receiver) return null;

  const receiverDetails = await User.findOne({username: receiver.username});

  return receiverDetails ? receiverDetails.socketId : null;
};

const notifyNewChat = (io, receiverSocketID) => {
  io.to(receiverSocketID).emit('new_chat');
};

const notifyRemoveChat = (io, receiverSocketID) => {
  io.to(receiverSocketID).emit('remove_chat');
};

module.exports = {
  findReceiver,
  notifyNewChat,
  notifyRemoveChat,
  createChat, addMessage, deleteChat, findChatByUser, findChatById, getChatsByID
};