package com.example.ap2_ex4.api;

import java.util.List;

public class AllMessagesFromChat {
//    private List<MessageFormatFromServer> messages;
    private String id;
    private MessageFormatFromServer[] messages;
    private UserFromServer[] users;

    public AllMessagesFromChat(String id, MessageFormatFromServer[] messages, UserFromServer[] users) {
        this.id = id;
        this.messages = messages;
        this.users = users;
    }
//    private List<UserFromServer> users;

//    public AllMessagesFromChat(List<MessageFormatFromServer> messages, String id, List<UserFromServer> users) {
//        this.messages = messages;
//        this.id = id;
//        this.users = users;
//    }
}
