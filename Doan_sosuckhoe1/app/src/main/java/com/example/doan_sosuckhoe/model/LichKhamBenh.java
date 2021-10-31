package com.example.doan_sosuckhoe.model;

public class LichKhamBenh {
    //thêm phone
    public String BenhVien;
    public String Khoa;
    public String BacSi;
    public String DonVi;

    public String ThoiGian;
    public String LyDoKham;
    public String GhiChu;
    public String phone;

    public LichKhamBenh() {
    }

    public LichKhamBenh(String phone) {
        this.BenhVien = "";
        this.Khoa ="";
        this.BacSi = "";
        this.DonVi = "";
        this.ThoiGian = "";
        this.LyDoKham = "";
        this.GhiChu = "";
        this.phone = phone;
    }

    public LichKhamBenh(String phone, String benhVien, String khoa, String bacSi, String donVi, String thoiGian, String lyDoKham, String ghiChu) {
        BenhVien = benhVien;
        khoa = Khoa;
        BacSi = bacSi;
        DonVi = donVi;
        ThoiGian = thoiGian;
        LyDoKham = lyDoKham;
        GhiChu = ghiChu;
        this.phone = phone;
    }

}
