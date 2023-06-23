package com.example.ap2_ex4.api;

public class LastAddedContact {
    private String id;
    private UserFromServer user;

    public LastAddedContact(UserFromServer contact, String chatId) {
        this.id = chatId;
        this.user = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserFromServer getContact() {
        return user;
    }

    public void setContact(UserFromServer contact) {
        this.user = contact;
    }
}
