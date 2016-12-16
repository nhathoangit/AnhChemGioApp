package com.example.jerem.anhchemgioapp.model;

/**
 * Created by jerem on 05/12/2016.
 */

public class Conversation {
    private String name;
    private People people;
    private Message message;

    public Conversation() {
    }

    public Conversation(String name, People people, Message message) {
        this.name = name;
        this.people = people;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
