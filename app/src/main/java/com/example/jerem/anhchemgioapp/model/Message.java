package com.example.jerem.anhchemgioapp.model;
import java.util.Date;
/**
 * Created by jerem on 05/12/2016.
 */

public class Message {
    private String content;
    private long time;
    private String userID;

    public Message() {
    }

    public Message(String content, String userID) {
        this.content = content;
        this.time = new Date().getTime();
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
