package com.example.alabroormobile.model;

public class User {
    private String id;
    private String username;
    private String email;
    private String gambar;

    public User() {
    }

    public User(String id, String username, String email, String gambar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGambar() {
        return gambar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}