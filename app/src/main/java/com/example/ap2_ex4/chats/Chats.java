package com.example.ap2_ex4.chats;

import java.util.List;
import com.example.ap2_ex4.api.Chat;
import com.example.ap2_ex4.api.UserAPI;
import com.example.ap2_ex4.api.CallbackResponse;

public class Chats {
    private List<Chat> chats;

    public Chats() {
        chats = null;
        chats = getChats();
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
