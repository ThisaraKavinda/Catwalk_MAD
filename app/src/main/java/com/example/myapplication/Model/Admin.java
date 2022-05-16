package com.example.myapplication.Model;

public class Admin {

    String name;
    String mobile;
    String password;
    String imageUrl;

    public Admin() {
    }

    public Admin(String name, String mobile, String password, String imageUrl) {
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
