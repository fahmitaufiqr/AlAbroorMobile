package com.example.alabroormobile.model;

import java.io.Serializable;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class UserModel implements Serializable {

        private String idEmail,email,name,gambar,numberPhone;

        public UserModel() {
        }

        public UserModel(String id, String email, String name, String gambar, String numberPhone) {
                this.idEmail = id;
                this.email = email;
                this.name = name;
                this.gambar = gambar;
                this.numberPhone = numberPhone;
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


        public void setGambar(String gambar) {
                this.gambar = gambar;
        }

        public String getNumberPhone() {
                return numberPhone;
        }

        public void setNumberPhone(String numberPhone) {
                this.numberPhone = numberPhone;
        }

}
