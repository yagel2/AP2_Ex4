package com.example.ap2_ex4;

public class User {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, String password, String displayName) {

        //fix the image

        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = String.valueOf(R.drawable.person_circle);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
