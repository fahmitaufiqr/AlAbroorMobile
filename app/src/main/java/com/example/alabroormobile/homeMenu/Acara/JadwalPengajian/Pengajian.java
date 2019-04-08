package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

import java.io.Serializable;

public class Pengajian implements Serializable {

    private String nama;
    private String keterangan;
    private String date;
    private String time;
    private String key;
    private String pengirim;


    public Pengajian() {
    }

    public Pengajian(String nama, String keterangan, String date, String time, String key, String pengirim) {
        this.nama = nama;
        this.keterangan = keterangan;
        this.date = date;
        this.time = time;
        this.key = key;
        this.pengirim = pengirim;
    }


    public String getPengirim() {
        return pengirim;
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
