package com.garren.roomate.models;

import java.util.HashMap;

public class Room {
    private String key;
    private String name;
    private String password;
    private HashMap<String, Post> posts;

    private Room() {
    }

    public Room(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public HashMap<String, Post> getPosts() {
        return this.posts;
    }

}
