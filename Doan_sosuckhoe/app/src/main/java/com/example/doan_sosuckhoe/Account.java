package com.example.doan_sosuckhoe;

public class Account {
    public String fullName;
    public String phoneNumber;
    public String password;
    public String rePassword;

    public Account() {
        //empty
    }

    public Account(String fullName, String phoneNumber, String password, String rePassword) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.rePassword = rePassword;
    }
}
