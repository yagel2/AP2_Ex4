package com.example.ap2_ex4.contacts;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @NonNull
    @PrimaryKey
    private String id;
    private String chatId;
    private String lastTime;
    private final int profilePic;
    private final String displayName;

    public Contact(String displayName, String chatId, int profilePic) {
        this.id = chatId;
        this.lastTime = "";
        this.chatId = chatId;
        this.profilePic = profilePic;
        this.displayName = displayName;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public String getLastTime() {
        return lastTime;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}