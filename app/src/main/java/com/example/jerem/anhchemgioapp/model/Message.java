package com.example.jerem.anhchemgioapp.model;

import java.sql.Timestamp;

/**
 * Created by jerem on 05/12/2016.
 */

public class Message {
    private String content;
    private Timestamp time;
    private String userID;

    public Message() {
    }

    public Message(String content, Timestamp time, String userID) {
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
