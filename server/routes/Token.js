const express = require('express');
const router = express.Router();
const tokenController = require('../controllers/Token');

router.post('/', tokenController.login);

module.exports = router;

