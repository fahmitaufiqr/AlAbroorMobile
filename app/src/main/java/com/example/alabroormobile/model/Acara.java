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

    public String getKey() {
        return key;
    }

    public void setKey(String id, String key) {
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

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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

    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> acaraa =  new HashMap<String,String>();
        acaraa.put("key",key);
        acaraa.put("nama", nama);
        acaraa.put("keterangan", keterangan);
        acaraa.put("date", date);
        acaraa.put("time", time);

        return acaraa;
    }
}
