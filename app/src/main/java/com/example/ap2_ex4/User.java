package com.example.ap2_ex4;

public class User {
    private final String username;
    private final String profilePic;

    public User(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePic() {
        return profilePic;
    }

}
