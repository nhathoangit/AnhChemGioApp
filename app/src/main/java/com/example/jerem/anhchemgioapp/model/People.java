package com.example.jerem.anhchemgioapp.model;

/**
 * Created by jerem on 05/12/2016.
 */

public class People {
    private String userID;
    private boolean status;

    public People() {
    }

    public People(String userID, boolean status) {
        this.userID = userID;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
