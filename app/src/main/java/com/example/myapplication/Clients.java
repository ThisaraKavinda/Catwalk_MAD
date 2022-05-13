package com.example.myapplication;

public class Clients {
    String name;
    String password;
    String email;
    String mobile;
    String company;
    String location;
    String imageurl;
    String status;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
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

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getImageurl() {
        return imageurl;
    }



    public Clients() {

    }

    public Clients(String name, String password, String email, String mobile, String company, String location, String imageurl,String status) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.company = company;
        this.location = location;
        this.imageurl = imageurl;
        this.status= status;
    }



}
