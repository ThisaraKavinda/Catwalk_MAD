package com.example.myapplication;

public class Articles {

    String topic;
    String description;

    public Articles() {

    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public Articles(String topic, String description) {
        this.topic = topic;
        this.description = description;
    }
}
