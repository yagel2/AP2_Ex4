package com.example.ap2_ex4.api;

public class AllMessagesFromChat {
    private String id;
    private UserFromServer[] users;
    private MessageFromServer[] messages;

    public AllMessagesFromChat(String id, MessageFromServer[] messages, UserFromServer[] users) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }
}
