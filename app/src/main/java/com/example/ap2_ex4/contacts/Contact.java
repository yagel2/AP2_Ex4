package com.example.ap2_ex4.contacts;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String serverId;
    private String lastTime;
    private final int profilePic;
    private final String displayName;

    public Contact(String displayName, String lastTime, int profilePic) {
        this.lastTime = lastTime;
        this.profilePic = profilePic;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getServerId() {
        return serverId;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}

