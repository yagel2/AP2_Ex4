const express = require('express');
const router = express.Router();
const chatsController = require('../controllers/Chats');
const tokenController = require('../controllers/Token');

router.route('/')
    .get(tokenController.verifyToken, chatsController.getChats)
    .post(tokenController.verifyToken, chatsController.createChat);
router.route('/:id')
    .get(tokenController.verifyToken, chatsController.getChatsByID)
    .delete(tokenController.verifyToken, chatsController.deleteChat);

module.exports = router;