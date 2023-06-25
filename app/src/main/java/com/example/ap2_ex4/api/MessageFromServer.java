package com.example.ap2_ex4.api;

public class MessageFromServer {
    private final String created;
    private final String content;
    private final UserFromServer sender;

    public MessageFromServer(String created, UserFromServer sender, String content) {
        this.sender = sender;
        this.created = created;
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }

    public UserFromServer getSender() {
        return sender;
    }


}