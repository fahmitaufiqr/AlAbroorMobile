package com.example.alabroormobile.model;

public class User {
    private String id, username, phone;

    public User() {
    }

    public User(String id, String username, String phone) {
        this.id = id;
        this.username = username;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}