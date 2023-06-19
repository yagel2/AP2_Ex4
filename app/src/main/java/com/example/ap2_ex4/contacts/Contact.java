package com.example.ap2_ex4.contacts;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private final int id;
    private final int profilePic;
    private final String lastTime;
    private final String displayName;

    public Contact(int id, String displayName, String lastTime, int profilePic) {
        this.id = id;
        this.lastTime = lastTime;
        this.profilePic = profilePic;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLastTime() {
        return lastTime;
    }

    public int getProfilePic() {
        return profilePic;
    }
}
