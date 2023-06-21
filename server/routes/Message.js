const express = require('express');
const router = express.Router();
const tokenController = require('../controllers/Token');
const messageController = require('../controllers/Message');

router.route('/:id/Messages')
    .get(tokenController.verifyToken, messageController.getMessages)
    .post(tokenController.verifyToken, messageController.createMessage)

module.exports = router;