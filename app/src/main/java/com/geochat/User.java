package com.geochat;


import com.google.gson.annotations.Expose;

import java.io.File;


public class User {
    @Expose
    private String name;
    @Expose
    private String birthdate;
    @Expose
    private String phone_number;
    @Expose
    private String gender;
    @Expose
    private String device_id;
    @Expose
    private File image;

    public User(String name, String birthdate, String phone_number, String gender, String device_id, File image) {
        this.name = name;
        this.birthdate = birthdate;
        this.phone_number = phone_number;
        this.gender = gender;
        this.device_id = device_id;
        this.image = image;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
