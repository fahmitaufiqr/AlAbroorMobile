package com.example.alabroormobile.model;

import java.util.HashMap;

public class Pengurus {
    private String nama;
    private String email;
    private String key;

    public Pengurus() {
    }

    public Pengurus(String nama, String email) {
        this.nama = nama;
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+nama+"\n" +
                " "+email;
    }

    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> acaraa =  new HashMap<String,String>();
        acaraa.put("key",key);
        acaraa.put("nama", nama);
        acaraa.put("email", email);

        return acaraa;
    }
}
