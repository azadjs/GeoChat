package com.geochat;

import com.google.gson.annotations.SerializedName;

public final class User {
    @SerializedName("name")
    private static String name;
    @SerializedName("birthdate")
    private static String birthdate;
    @SerializedName("phone_number")
    private static String phone_number;
    @SerializedName("gender")
    private static String gender;
    @SerializedName("device_id")
    private static String device_id;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getBirthdate() {
        return birthdate;
    }

    public static void setBirthdate(String birthdate) {
        User.birthdate = birthdate;
    }

    public static String getPhone_number() {
        return phone_number;
    }

    public static void setPhone_number(String phone_number) {
        User.phone_number = phone_number;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        User.gender = gender;
    }

    public static String getDevice_id() {
        return device_id;
    }

    public static void setDevice_id(String device_id) {
        User.device_id = device_id;
    }
}
