const express = require('express');
const app = express();

const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json({limit: '5mb'}));
app.use(express.json());

const cors = require('cors');
app.use(cors());

const customEnv = require('custom-env');
customEnv.env(process.env.NODE_ENV, './config');

const mongoose = require('mongoose');
mongoose.connect(process.env.CONNECTION_STRING, {
  useNewUrlParser: true,
  useUnifiedTopology: true
});

app.use(express.static('public'))

const users = require('./routes/User');
app.use('/api/Users', users);

const token = require('./routes/Token');
app.use('/api/Tokens', token);

const chats = require('./routes/Chats');
app.use('/api/Chats', chats);

const messages = require('./routes/Message');
app.use('/api/Chats', messages);

const http = require('http');
const {Server} = require("socket.io");
const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: "*",
    methods: ["GET", "POST", "DELETE", "PUT"]
  }
});

io.on("connection", async (socket) => {
  socket.on("send_message",  () => {
    socket.broadcast.emit("sent_message");
  });

  socket.on("add-chat",  () => {
    socket.broadcast.emit("added_chat");
  });

  socket.on("remove-chat",  () => {
    socket.broadcast.emit("removed_chat");
  });
});

const path = require('path');
const buildPath = path.join(__dirname, 'public', 'build');
app.use(express.static(buildPath));

app.get('*', (req, res) => {
  res.sendFile(path.join(buildPath, 'index.html'));
});

server.listen(process.env.PORT);