package com.example.ap2_ex4.api;
public class UserFromServer {
    private String username;
    private String displayName;
    private String profilePic;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserFromServer(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
//        this.profilePic = String.valueOf(R.drawable.person_circle);
        this.profilePic = profilePic;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImage() {
        return profilePic;
    }

    public void setImage(String image) {
        this.profilePic = image;
    }
}