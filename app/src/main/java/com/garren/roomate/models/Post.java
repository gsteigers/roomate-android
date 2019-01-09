package com.garren.roomate.models;

public class Post {
    private String key;
    private String message;
    private String user;

    private Post() {
    }

    public Post(String message, String user) {
        this.message = message;
        this.user = user;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String getMessage() {
        return this.message;
    }

    public String getUser() {
        return this.user;
    }
}
