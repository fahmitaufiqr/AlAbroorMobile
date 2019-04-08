package com.example.alabroormobile.model;

public class JadwalPetugas {
    String tanggal, muazin, imam;

    public JadwalPetugas() {
    }

    public JadwalPetugas(String tanggal, String muazin, String imam) {
        this.tanggal = tanggal;
        this.muazin = muazin;
        this.imam = imam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getMuazin() {
        return muazin;
    }

    public void setMuazin(String muazin) {
        this.muazin = muazin;
    }

    public String getImam() {
        return imam;
    }

    public void setImam(String imam) {
        this.imam = imam;
    }
}
