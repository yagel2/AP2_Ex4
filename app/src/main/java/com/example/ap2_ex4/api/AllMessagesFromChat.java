package com.example.ap2_ex4.api;

public class AllMessagesFromChat {
    private String id;
    private UserFromServer[] users;
    private MessageFormatFromServer[] messages;

    public AllMessagesFromChat(String id, MessageFormatFromServer[] messages, UserFromServer[] users) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }
}
