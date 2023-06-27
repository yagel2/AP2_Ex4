# Advanced programming project - instant messaging service

How to run: Download all files and folders from GitHub to your android project. Download node modules and dependencies for server folder by the command 'npm install'. Then run a local mongodb server with mongodb port (27017) with address mongodb://127.0.0.1:27017. Then, you can run the server from the server directory, by the command 'npm start'. After that, Choose an emulator of your choice (we used Pixel 6 API 30) and run the app.

**Unfortunately, we did not have time to implement the Firebase part, so the application works without notifications between the clients.**

**Registration activity** - in this section, you must fill in all the fields including uploading a profile picture, the password must contain a combination of numbers and letters and be at least 8 characters long.

**Login activity** - in this section you must fill in the user information (username and password) with which you registered for our application.

**Contacts menu activity** - in this section you can add contacts registered to the application by using the add button in the lower right part of the screen, you can also scroll through the list of contacts and select the contact you want to chat with.
For your convenience, your username and profile picture will be displayed at the top of the screen next to the settings button.

**Chat screen activity** - on this screen you can chat with the contact you selected. We will make sure to save the message history for you so that you can leave the chat with peace of mind and return to the conversation at your leisure.

**Settings activity** - in this section you can choose to change the language from English to Hebrew and vice versa, as well as change the theme between day and night mode. You can also edit the server address with which the application communicates in the following format: http://ip:port, for example: http://127.0.0.1:55555.
Also, a logout button is attached to protect your privacy.

Enjoy visiting the app :)