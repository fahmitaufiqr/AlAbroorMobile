package com.example.alabroormobile.activity.GoogleLogin;

import java.io.Serializable;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class UserModel implements Serializable {

        private String idEmail,email,name,gambar,umur,noHp,status;

        public UserModel() {
        }

        public UserModel(String id, String email, String name, String gambar, String umur, String noHp, String status) {
                this.idEmail = id;
                this.email = email;
                this.name = name;
                this.gambar = gambar;
                this.umur = umur;
                this.noHp = noHp;
                this.status = status;
        }

        public String getEmail() {
        return email;
        }

        public void setEmail(String email) {
        this.email = email;
        }

        public String getIdEmail() {
                return idEmail;
        }

        public void setIdEmail(String idEmail) {
                this.idEmail = idEmail;
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

        public String getUmur() {
        return umur;
        }

        public String getNoHp() {
        return noHp;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }
}
