package com.example.ap2_ex4.api;

import com.example.ap2_ex4.User;

import java.util.Date;

public class MessageFormatFromServer {
    private Date created;
    private User sender;
    private String content;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public MessageFormatFromServer(Date created, User sender, String content) {
        this.created = created;
        this.sender = sender;
        this.content = content;
    }
}
