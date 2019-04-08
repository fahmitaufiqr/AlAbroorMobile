package com.example.alabroormobile.homeMenu.Acara.Ramadhan;

import java.io.Serializable;

/**
 * Created by Aulia Ikvanda on 07,April,2019
 */
public class RamadhanModel implements Serializable {
    private String buka,sahur,penceramah,tanggal,hariKe,key;

    public RamadhanModel() {
    }

    public RamadhanModel(String buka, String sahur, String penceramah, String tanggal, String hariKe, String key) {
        this.buka = buka;
        this.sahur = sahur;
        this.penceramah = penceramah;
        this.tanggal = tanggal;
        this.hariKe = hariKe;
        this.key = key;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHariKe() {
        return hariKe;
    }

    public void setHariKe(String hariKe) {
        this.hariKe = hariKe;
    }

    public String getBuka() {
        return buka;
    }

    public void setBuka(String buka) {
        this.buka = buka;
    }

    public String getSahur() {
        return sahur;
    }

    public void setSahur(String sahur) {
        this.sahur = sahur;
    }

    public String getPenceramah() {
        return penceramah;
    }

    public void setPenceramah(String penceramah) {
        this.penceramah = penceramah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
