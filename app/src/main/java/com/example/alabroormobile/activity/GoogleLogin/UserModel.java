package com.example.alabroormobile.activity.GoogleLogin;

import java.io.Serializable;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class UserModel implements Serializable {
private String id,email,name,gambar,umur,noHp;

public UserModel() {
        }

        public UserModel(String id, String email, String name, String gambar, String umur, String noHp) {
                this.id = id;
                this.email = email;
                this.name = name;
                this.gambar = gambar;
                this.umur = umur;
                this.noHp = noHp;
        }

        public String getEmail() {
        return email;
        }

public void setEmail(String email) {
        this.email = email;
        }

public String getId() {
        return id;
        }

public void setId(String id) {
        this.id = id;
        }

public String getName() {
        return name;
        }

public void setName(String name) {
        this.name = name;
        }

        public String getGambar() {
                return gambar;
        }

        public void setGambar(String gambar) {
                this.gambar = gambar;
        }

        public String getUmur() {
        return umur;
        }

public void setUmur(String umur) {
        this.umur = umur;
        }

public String getNoHp() {
        return noHp;
        }

public void setNoHp(String noHp) {
        this.noHp = noHp;
        }

}
