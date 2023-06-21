package com.example.ap2_ex4.api;
import com.example.ap2_ex4.User;
import java.util.List;
public class Chat {
    private List<User> users; //contacts
    private List<MessageFormatFromServer> messages;
    public Chat(List<User> users, List<MessageFormatFromServer> messages) {
        this.users = users;
        this.messages = messages;
    }
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<MessageFormatFromServer> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageFormatFromServer> messages) {
        this.messages = messages;
    }
}
