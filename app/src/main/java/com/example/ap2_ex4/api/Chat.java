package com.example.ap2_ex4.api;

public class Chat {
    private String id;
    private UserFromServer user;
    private MessageFormatFromServer lastMessage;

    public Chat(UserFromServer user, MessageFormatFromServer lastMessage, String id) {
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

    public void setUser(UserFromServer user) {
        this.user = user;
    }

    public MessageFormatFromServer getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageFormatFromServer lastMessage) {
        this.lastMessage = lastMessage;
    }
}
