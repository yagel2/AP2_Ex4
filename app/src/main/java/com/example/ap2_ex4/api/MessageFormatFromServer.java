package com.example.ap2_ex4.api;
import java.util.Date;
public class MessageFormatFromServer {
    //    private String id;
    private String created;
    private String content;
    private UserFromServer sender;

    public MessageFormatFromServer(String created, UserFromServer sender, String content) {
        this.sender = sender;
        this.created = created;
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
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
}