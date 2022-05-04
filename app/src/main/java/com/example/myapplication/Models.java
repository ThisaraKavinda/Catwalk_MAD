package com.example.myapplication;

import android.view.Display;
import android.widget.EditText;

public class Models {

    String name;
    String password;
    String email;
    String mobile;
    String birthday;

    public Models(){

    }
    public Models(String name, String password, String email, String mobile, String birthday) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.birthday = birthday;
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
}
