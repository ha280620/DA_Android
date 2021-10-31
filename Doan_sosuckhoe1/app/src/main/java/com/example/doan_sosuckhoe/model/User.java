package com.example.doan_sosuckhoe.model;

public class User {
    public String HoTen;
    public String datetime;
    public String GioiTinh;
    public String phone;
    public String MaBHYT;
    public String CMND;
    public String DiaChi;
    public String email;
    public String NhomMau;
    public String CanNang;
    public String ChieuCao;
    public String TieuSu;
    public String Anh;

    public User() {
    }
    public User(String phone, String name) {
        this.HoTen = name;
        this.datetime = "";
        this.GioiTinh = "";
        this.phone = phone;
        this.MaBHYT = "";
        this.CMND = "";
        this.DiaChi = "";
        this.email = "";
    }
    public User(String hoTen, String datetime, String gioiTinh,  String maBHYT, String phone,String CMND, String diaChi, String email) {
        HoTen = hoTen;
        this.datetime = datetime;
        GioiTinh = gioiTinh;
        phone = this.phone;
        MaBHYT = maBHYT;
        this.CMND = CMND;
        DiaChi = diaChi;
        this.email = email;
    }
    public User(String hoTen, String datetime, String gioiTinh, String phone, String CMND, String diaChi) {
        HoTen = hoTen;
        this.datetime = datetime;
        GioiTinh = gioiTinh;
        phone = this.phone;
        this.CMND = CMND;
        DiaChi = diaChi;
    }

    public User(String hoTen, String datetime, String gioiTinh, String phone, String maBHYT, String CMND, String diaChi, String email, String nhomMau, String canNang, String chieuCao, String tieuSu) {
        HoTen = hoTen;
        this.datetime = datetime;
        GioiTinh = gioiTinh;
        this.phone = phone;
        MaBHYT = maBHYT;
        this.CMND = CMND;
        DiaChi = diaChi;
        this.email = email;
        NhomMau = nhomMau;
        CanNang = canNang;
        ChieuCao = chieuCao;
        TieuSu = tieuSu;
    }
}
