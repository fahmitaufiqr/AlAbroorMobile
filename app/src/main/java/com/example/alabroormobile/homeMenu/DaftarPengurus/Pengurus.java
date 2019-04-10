package com.example.alabroormobile.homeMenu.DaftarPengurus;

public class Pengurus {
    private String nama;
    private String email;
    private String key;
    private String status;

    public Pengurus() {
    }

    public Pengurus(String nama, String email, String status) {
        this.nama = nama;
        this.email = email;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                " "+email+"\n" +
                " "+status;
    }
}
