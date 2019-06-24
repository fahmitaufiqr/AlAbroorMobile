package com.example.alabroormobile.HomeMenu.Ramadhan;

import java.io.Serializable;

/**
 * Created by Aulia Ikvanda on 23,June,2019
 */
public class Ramadhan2Model implements Serializable {
    private String imam,qultum,hariKe,key;

    public Ramadhan2Model() {
    }

    public Ramadhan2Model(String imam, String qultum, String hariKe, String key) {
        this.imam = imam;
        this.qultum = qultum;
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