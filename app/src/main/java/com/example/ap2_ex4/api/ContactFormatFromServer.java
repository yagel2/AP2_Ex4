package com.example.ap2_ex4.api;

import com.example.ap2_ex4.User;

public class ContactFormatFromServer {
    private UserFromServer user;
    private String id;

    public ContactFormatFromServer(UserFromServer user, String id) {
        this.user = user;
        this.id = id;
    }

    public UserFromServer getUser() {
        return user;
    }

    public void setUser(UserFromServer user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}