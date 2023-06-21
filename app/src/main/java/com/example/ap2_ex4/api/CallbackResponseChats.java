package com.example.ap2_ex4.api;

import java.util.List;

public interface CallbackResponseChats {
    List<Chat> onResponse(boolean success);
}
