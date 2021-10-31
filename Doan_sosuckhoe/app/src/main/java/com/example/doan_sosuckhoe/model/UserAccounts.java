package com.example.doan_sosuckhoe.model;

public class UserAccounts {
    public String fullName;
    public String phone;
    public String password;
    public User info_user;



    public UserAccounts() {
    }

    public UserAccounts(String fullName, String phone, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.info_user = new User(phone,fullName);
    }

    public UserAccounts(String fullName, String phone, String password
            , Boolean isHost, String currentRoomId) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
    }

    public User getInfo_user() {
        return info_user;
    }
}
