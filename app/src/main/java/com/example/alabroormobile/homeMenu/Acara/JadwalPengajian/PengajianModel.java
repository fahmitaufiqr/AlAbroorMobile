package com.example.alabroormobile.homeMenu.Acara.JadwalPengajian;

/**
 * Created by Aulia Ikvanda on 10,April,2019
 */
public class PengajianModel {
    private String nama;
    private String keterangan;
    private String date;
    private String time;
    private String pengirim;

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

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public PengajianModel() {
    }

    public PengajianModel(String nama, String keterangan, String date, String time, String pengirim) {
        this.nama = nama;
        this.keterangan = keterangan;
        this.date = date;
        this.time = time;
        this.pengirim = pengirim;
    }

    @Override
    public String toString() {
        return nama + " - " + keterangan + " - " +date+ " - " +time+ " - " +pengirim;
    }
}

