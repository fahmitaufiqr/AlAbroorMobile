package com.example.alabroormobile.homeMenu.Acara.Ramadhan;

import java.io.Serializable;

/**
 * Created by Aulia Ikvanda on 07,April,2019
 */
public class RamadhanModel implements Serializable {
    private String imam,qultum,tanggal;

    public RamadhanModel() {
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public RamadhanModel(String imam, String qultum, String tanggal) {

        this.imam = imam;
        this.qultum = qultum;
        this.tanggal = tanggal;
    }

    public String getImam() {
        return imam;
    }

    public void setImam(String imam) {
        this.imam = imam;
    }

    public String getQultum() {
        return qultum;
    }

    public void setQultum(String qultum) {
        this.qultum = qultum;
    }
}
