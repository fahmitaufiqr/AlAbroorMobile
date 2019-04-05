package com.example.alabroormobile.model;

import java.io.Serializable;
import java.util.HashMap;

public class Acara implements Serializable {

    private String nama;
    private String keterangan;
    private String date;
    private String time;
    private String key;


    public Acara() {
    }

    public Acara(String nama, String keterangan, String date, String time) {
        this.nama = nama;
        this.keterangan = keterangan;
        this.date = date;
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return " "+nama+"\n" +
                " "+keterangan+"\n" +
                " "+date+"\n" +
                " "+time;
    }
}
