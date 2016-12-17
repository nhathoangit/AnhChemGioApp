package com.example.jerem.anhchemgioapp.model;

import java.util.ArrayList;

/**
 * Created by jerem on 05/12/2016.
 */

public class Conversation {
    private String name;
    private ArrayList<People> peoples;
    private ArrayList<Message> messages;

    public Conversation() {
    }

    public Conversation(String name, ArrayList<People> peoples, ArrayList<Message> messages) {
        this.name = name;
        this.peoples = peoples;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(ArrayList<People> peoples) {
        this.peoples = peoples;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
