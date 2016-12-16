package com.example.jerem.anhchemgioapp.model;

/**
 * Created by jerem on 04/12/2016.
 */

public class User {
    private String displayName;
    private String email;
    private boolean status;
    private String avatar;

    public User() {
    }

    public User(String displayName, String email, boolean status, String avatar) {
        this.displayName = displayName;
        this.email = email;
        this.status = status;
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
