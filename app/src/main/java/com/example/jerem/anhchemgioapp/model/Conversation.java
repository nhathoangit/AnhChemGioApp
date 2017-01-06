package com.example.jerem.anhchemgioapp.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by jerem on 05/12/2016.
 */

public class Conversation {
    private String conversationID;
    private String name;
    private String lastMessage;
    private long time;
    private Map<String,User> peoples;
    private Map<String, Message> messages;

    public Conversation(String name, Map<String, User> peoples, Map<String, Message> messages) {
        this.name = name;
        this.peoples = peoples;
        this.messages = messages;
        this.lastMessage = "";
        this.time =  new Date().getTime();
    }

    public Conversation() {
        this.time = new Date().getTime();
    }

    public Conversation(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, User> getPeoples() {
        return peoples;
    }

    public void setPeoples(Map<String, User> peoples) {
        this.peoples = peoples;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
