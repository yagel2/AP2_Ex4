package com.example.ap2_ex4.api;

import com.example.ap2_ex4.User;
import com.example.ap2_ex4.messages.Message;

import java.util.Date;
import java.util.List;

public class Chat {
    private List<User> users; //contacts
    private List<Message> messages;    //yagel

    public Chat(List<User> users, List<Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
