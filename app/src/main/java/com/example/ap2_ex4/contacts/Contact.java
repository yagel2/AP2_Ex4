package com.example.ap2_ex4.contacts;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String serverId;
    private String lastTime;
    private final String profilePic;
    private final String username;
    private final String displayName;
    public Contact(String username, String displayName, String serverId, String profilePic) {
        this.lastTime = "";
        this.serverId = serverId;
        this.username = username;
        this.profilePic = profilePic;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getServerId() {
        return serverId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
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

