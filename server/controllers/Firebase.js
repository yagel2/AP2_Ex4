const firebaseService = require('../services/Firebase')

const associateFirebaseToken = async (req, res) => {
  const {username} = req.username;
  const token = req.body.token;
  const result = await firebaseService.associateToken(username, token);
  if(result) {
    res.status(200).send("Token updated");
  } else {
    res.status(500).send("Error occurred while updating token");
  }
}

const deleteFirebaseToken = async (req, res) => {
  const {username} = req.username;
  const result = await firebaseService.deleteFirebaseToken(username);
  if(result) {
    res.status(200).send("token deleted");
  } else {
    res.status(500).send("Error occurred while deleting token");
  }
}

module.exports = {associateFirebaseToken, deleteFirebaseToken};
