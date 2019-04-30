package com.example.alabroormobile.model;

public class JadwalPetugas {
    String tanggal, muazin, imam, qultum;

    public JadwalPetugas() {
    }

    public JadwalPetugas(String tanggal, String muazin, String imam) {
        this.tanggal = tanggal;
        this.muazin = muazin;
        this.imam = imam;
    }

    public JadwalPetugas(String tanggal, String muazin, String imam, String qultum) {
        this.tanggal = tanggal;
        this.muazin = muazin;
        this.imam = imam;
        this.qultum = qultum;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getQultum() {
        return qultum;
    }

    public void setQultum(String qultum) {
        this.qultum = qultum;
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
