package com.example.alabroormobile.model;

import java.util.HashMap;

public class User2 {
    private String nama, email;

    public User2() {
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> userr =  new HashMap<String,String>();
        userr.put("nama", nama);
        userr.put("email", email);
        return userr;
    }
}
