package com.example.ap2_ex4.chats;

import com.example.ap2_ex4.User;
import com.example.ap2_ex4.api.CallbackResponse;
import com.example.ap2_ex4.api.Chat;
import com.example.ap2_ex4.api.UserAPI;

import java.util.List;

public class Chats {
    private List<Chat> chats;

    public Chats() {
        chats = null;
        UserAPI.getInstance().getChats(new CallbackResponse() {
            @Override
            public void onResponse(boolean success) {
                if (success) {
                    chats = UserAPI.getInstance().getAllChatsAfterServer();
                }
            }
        });
    }

    public List<Chat> getChats() {
        UserAPI.getInstance().getChats(new CallbackResponse() {
            @Override
            public void onResponse(boolean success) {
                if (success) {
                    chats = UserAPI.getInstance().getAllChatsAfterServer();
                }
            }
        });
        return chats;
    }
}
