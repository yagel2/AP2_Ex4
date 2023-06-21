package com.example.ap2_ex4.api;
import com.example.ap2_ex4.User;
import java.util.List;
public class Chat {


//    id: chat.id,
//    user: contact,
//    lastMessage: chat.messages[0]


//    private List<UserFromServer> users; //contacts
//    private List<MessageFormatFromServer> messages;
    private UserFromServer user;
    private MessageFormatFromServer lastMessage;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chat(UserFromServer user, MessageFormatFromServer lastMessage, String id) {
        this.user = user;
        this.lastMessage = lastMessage;
        this.id = id;
    }
}
