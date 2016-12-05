package com.example.jerem.anhchemgioapp.model;

/**
 * Created by jerem on 05/12/2016.
 */

public class Message {
    private String content;
    private int time;
    private String userID;

    public Message(String content, int time, String userID) {
        this.content = content;
        this.time = time;
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
