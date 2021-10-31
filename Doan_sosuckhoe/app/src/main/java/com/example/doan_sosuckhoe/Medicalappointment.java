package com.example.doan_sosuckhoe;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_sosuckhoe.Session.SessionManager;
import com.example.doan_sosuckhoe.firebase.realtimedatabase.UserAccountsRequester;
import com.example.doan_sosuckhoe.model.LichKhamBenh;
import com.example.doan_sosuckhoe.model.User;
import com.example.doan_sosuckhoe.model.UserAccounts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Medicalappointment extends AppCompatActivity {
    SessionManager sessionManager;
    UserAccountsRequester userAccountsRequester;
    EditText txt_benhvien, txt_khoa, txt_bacsi, txt_donvi, txt_tg, txt_lydo, txt_ghichu;
    TextView txt_lich_su_dat;
    Button btn_luu;
    String benhvien,khoa,bacsi,donvi,tg,lydo,ghichu;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicalappointment);

        sessionManager = new SessionManager(this);
        userAccountsRequester = new UserAccountsRequester(this);

        txt_benhvien = findViewById(R.id.txt_benhvien);
        txt_bacsi = findViewById(R.id.txt_bacsi);
        txt_khoa = findViewById(R.id.txt_khoa);
        txt_tg = findViewById(R.id.txt_tg);
        txt_donvi =(EditText) findViewById(R.id.txt_donvi);
        txt_lydo = findViewById(R.id.txt_lydo);
        txt_ghichu = findViewById(R.id.txt_ghichu);
        txt_lich_su_dat = findViewById(R.id.lich_su_dat);
        btn_luu = findViewById(R.id.btn_luu);
        back = findViewById(R.id.tro_lai);


        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Luu LKB");
                 benhvien = txt_benhvien.getText().toString();
                 bacsi = txt_bacsi.getText().toString();
                 tg = txt_tg.getText().toString();
                 khoa = txt_khoa.getText().toString();
                 donvi = "a"; //txt_donvi.getText().toString()
                 lydo = txt_lydo.getText().toString();
                 ghichu = txt_ghichu.getText().toString();

                System.out.println(benhvien+bacsi+"....");

                LichKhamBenh temp = new LichKhamBenh(sessionManager.getPhone());
                temp.BenhVien = benhvien;
                temp.BacSi = bacsi;
                temp.DonVi = donvi;
                temp.GhiChu = ghichu;
                temp.Khoa = khoa;
                temp.ThoiGian = tg;
                temp.LyDoKham = lydo;
                userAccountsRequester.updateLichKham(sessionManager.getPhone(), temp);

            }
        });
        txt_lich_su_dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Medicalappointment.this, Reminder.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Medicalappointment.this, Homepage.class);
                startActivity(intent);
            }
        });

    }
}