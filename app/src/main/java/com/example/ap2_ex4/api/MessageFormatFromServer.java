package com.example.ap2_ex4.api;
import java.util.Date;
public class MessageFormatFromServer {
    private String created;
    private UserFromServer sender;
    private String content;
//    private String id;

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
    public MessageFormatFromServer(String created, UserFromServer sender, String content) {
        this.created = created;
        this.sender = sender;
        this.content = content;
    }
}