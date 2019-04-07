package com.example.alabroormobile.AdminController;

import java.io.Serializable;

/**
 * Created by Aulia Ikvanda on 05,April,2019
 */
public class AdminModel implements Serializable {
    String username,password;

    public AdminModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AdminModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
