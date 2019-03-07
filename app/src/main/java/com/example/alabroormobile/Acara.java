package com.example.alabroormobile;

import java.io.Serializable;
import java.util.HashMap;

public class Acara implements Serializable {

    private String nama;
    private String keterangan;
    private String date;

    public Acara() {
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

    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> acaraa =  new HashMap<String,String>();
        acaraa.put("nama", nama);
        acaraa.put("keterangan", keterangan);
        acaraa.put("date", date);

        return acaraa;
    }
}
