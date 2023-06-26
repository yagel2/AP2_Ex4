const express = require('express');
const router = express.Router();
const tokenController = require('../controllers/Token');
const firebaseController = require('../controllers/Firebase');

router.route('/')
    .post(tokenController.verifyToken, firebaseController.associateFirebaseToken)
    .delete(tokenController.verifyToken, firebaseController.deleteFirebaseToken);

module.exports = router;