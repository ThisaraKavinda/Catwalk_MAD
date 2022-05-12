package com.example.myapplication;

import android.view.Display;
import android.widget.EditText;

import javax.net.ssl.SSLEngineResult;

public class Models {

    String name;
    String password;
    String email;
    String mobile;
    String birthday;
    String status;
    String gender;
    String imageurl;

    public Models(){

    }
    public Models(String name, String password, String email, String mobile, String birthday,String status,String gender,String imageurl) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.birthday = birthday;
        this.status = status;
        this.gender = gender;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getStatus() {
        return status;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getGender() {
        return gender;
    }
}
