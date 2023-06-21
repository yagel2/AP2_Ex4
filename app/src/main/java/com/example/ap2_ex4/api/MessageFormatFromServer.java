package com.example.ap2_ex4.api;
import java.util.Date;
public class MessageFormatFromServer {
    private Date created;
    private UserFromServer sender;
    private String content;
    private String id;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserFromServer getSender() {
        return sender;
    }

    public void setSender(UserFromServer sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public MessageFormatFromServer(Date created, UserFromServer sender, String content, String id) {
        this.created = created;
        this.sender = sender;
        this.content = content;
        this.id = id;
    }
}