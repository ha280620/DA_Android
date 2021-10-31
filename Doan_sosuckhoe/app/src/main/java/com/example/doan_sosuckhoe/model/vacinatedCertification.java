package com.example.doan_sosuckhoe.model;

public class vacinatedCertification {
    String phone_user;
    //class nay chua thong tin gi cua user m them may thuoc tinh vo di
    String ngaysinh;
    String hoten;
    String anh_maqr;

    public vacinatedCertification() {
    }

    public vacinatedCertification(String phone_user, String ngaysinh, String hoten) {
        this.phone_user = phone_user;
        this.ngaysinh = ngaysinh;
        this.hoten = hoten;
    }
}
