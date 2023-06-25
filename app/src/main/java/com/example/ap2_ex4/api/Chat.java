package com.example.ap2_ex4.api;

public class Chat {
    private String id;
    private final UserFromServer user;
    private final MessageFromServer lastMessage;

    public Chat(UserFromServer user, MessageFromServer lastMessage, String id) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserFromServer getUser() {
        return user;
    }

    public MessageFromServer getLastMessage() {
        return lastMessage;
    }
}
