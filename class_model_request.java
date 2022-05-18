package com.example.myapplication;

public class class_model_request {

    private String title;
    private String gender;
    private String height;
    private String type;
    private String payment;
    private String time;
    private String description;

    public class_model_request(){

    }

    public class_model_request(String title, String gender, String height, String type, String payment, String time, String description) {
        this.title = title;
        this.gender = gender;
        this.height = height;
        this.type = type;
        this.payment = payment;
        this.time = time;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
