package com.example.alabroormobile.activity.GoogleLogin;

import java.util.HashMap;

/**
 * Created by Aulia Ikvanda on 31,March,2019
 */

public class UserModel {
    private String fullName;
    private String photo;
    private String email;
    private HashMap<String,Object> timestampJoined;

    public UserModel() {
    }

    /**
     * Use this constructor to create new User.
     * Takes user name, email and timestampJoined as params
     *
     * @param timestampJoined
     */
    public UserModel(String mFullName, String mPhoneNo, String mEmail, HashMap<String, Object> timestampJoined) {
        this.fullName = mFullName;
        this.photo = mPhoneNo;
        this.email = mEmail;
        this.timestampJoined = timestampJoined;
    }


    public String getFullName() {
        return fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Object> getTimestampJoined() {
        return timestampJoined;
    }
}
