const express = require('express');
const router = express.Router();
const userController = require('../controllers/User');
const tokenController = require('../controllers/Token');

router.route('/').post(userController.createUser);
router.route('/:username').get(tokenController.verifyToken, userController.getUser)

module.exports = router;