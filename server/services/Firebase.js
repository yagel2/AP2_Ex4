const User = require('../models/User');
const admin = require('firebase-admin');
const MAX_NOTIFICATION_BODY_LENGTH = 100;

const associateToken = async (username, token) => {
  try {
    const user = await User.findOne({username});
    if (user) {
      user.firebaseToken = token;
      await user.save();
      console.log("firebaseToken: " + token + "added to: " + username);
      return 'success';
    } else {
      console.log('User not found: ' + username + ', token: ' + token);
      return null;
    }
  } catch (error) {
    console.error('Error occurred while updating current token:', error);
    return null;
  }
}

const deleteFirebaseToken = async (username) => {
  try {
    const user = await User.findOne({username});
    if (user) {
      user.firebaseToken = null;
      await user.save();
      console.log("firebaseToken removed from: " + username);
    } else {
      console.log('User not found:'  + username);
    }
  } catch (error) {
    console.error('Error occurred while updating current token:', error);
  }
}

const getFirebaseToken = async (userId) => {
  try {
    return (await User.findOne({_id: userId})).firebaseToken;
  } catch (error) {
    console.log("Error occurred while getting firebase token")
    return null;
  }
}

const notify = async (firebaseToken, message) => {
  let notificationBody = message.content;
  if (notificationBody.length > MAX_NOTIFICATION_BODY_LENGTH) {
    notificationBody = notificationBody.substring(0, MAX_NOTIFICATION_BODY_LENGTH) + '...';
  }

  try {
    const notification = {
      token: firebaseToken,
      notification: {
        title: message.sender,
        body: notificationBody,
      },
      data: {
        message: JSON.stringify(message),
      },
    };

    await admin.messaging().send(notification);

    console.log('Notification ' + message.content +' sent successfully.');
  } catch (error) {
    console.error('Error occurred while sending notification: ', error);
  }
}

module.exports = {associateToken, deleteFirebaseToken, getFirebaseToken, notify}