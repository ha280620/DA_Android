package com.example.doan_sosuckhoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_sosuckhoe.R;
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

public class Reminder extends AppCompatActivity {
    private DatabaseReference mDatabase;
    SessionManager sessionManager;
    UserAccountsRequester userAccountsRequester;
    TextView txt_lichhen;
    Button btn_dat_lich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        txt_lichhen = findViewById(R.id.lichhen);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sessionManager = new SessionManager(this);
        btn_dat_lich = findViewById(R.id.btn_dat_lich);

        userAccountsRequester = new UserAccountsRequester(this);
        userAccountsRequester.getInfo_User(sessionManager.getPhone());

        Query query = mDatabase.child("Appointment")
                .orderByChild("phone")
                .equalTo(sessionManager.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) { ;
                        LichKhamBenh tempLKB = user.getValue(LichKhamBenh.class);
                        txt_lichhen.setText(tempLKB.BenhVien +"-"+ tempLKB.BacSi);

                        System.out.println("cập nhật hồ sơ thành công");
                    }
                } else {
                    System.out.println("cập nhật hồ sơ thất bại");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_dat_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reminder.this, Medicalappointment.class);
                startActivity(intent);
            }
        });


    }
}