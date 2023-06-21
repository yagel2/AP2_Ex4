package com.example.ap2_ex4.api;

import com.example.ap2_ex4.User;

public class ContactFormatFromServer {
    private User user;
    private int id;

    public ContactFormatFromServer(User user, int id) {
        this.user = user;
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
