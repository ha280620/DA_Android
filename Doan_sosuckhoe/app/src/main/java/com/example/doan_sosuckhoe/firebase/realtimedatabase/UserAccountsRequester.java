package com.example.doan_sosuckhoe.firebase.realtimedatabase;

import android.content.Context;

import com.example.doan_sosuckhoe.model.LichKhamBenh;
import com.example.doan_sosuckhoe.model.User;
import com.example.doan_sosuckhoe.model.UserAccounts;
import com.example.doan_sosuckhoe.RootAttr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserAccountsRequester {
    private DatabaseReference mDatabase;
    private Context mContext;
    private static   User info_User;


    public UserAccountsRequester(Context context){
        mDatabase = FirebaseDatabase
                .getInstance("https://so-y-te-dien-tu-default-rtdb.firebaseio.com")
                .getReference();
        this.mContext = context;
    }

    //Tạo một tài khoản
    public String createAnUserAccount(UserAccounts userAccount) {
        String accKey =  mDatabase.child("Account").push().getKey();
        if (accKey != null) {
            mDatabase.child("Account").child(accKey).setValue(userAccount);
            return accKey;
        }
        return null;
    }

    //update info user
    public void updateUserAccount(String phone, User userInfo){
        Query query = mDatabase.child("Account")
                .orderByChild("phone")
                .equalTo(phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        String key = user.getKey();
                        mDatabase.child("Account")
                                .child(key).child("info_user")
                                .setValue(userInfo);
                        System.out.println("cập nhật hồ sơ trên firebase thành công");
                    }
                } else {
                    System.out.println("cập nhật hồ sơ thất bại");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //cap nhat lich kham benh
    public void updateLichKham(String phone, LichKhamBenh lichKhamBenh){
        Query query = mDatabase.child("Appointment")
                .orderByChild("phone")
                .equalTo(phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot lkb : dataSnapshot.getChildren()) {
                        String key = lkb.getKey();
                        mDatabase.child("Appointment")
                                .child(key)
                                .setValue(lichKhamBenh);
                        System.out.println("cập nhật lịch khám bệnh trên firebase thành công");
                    }
                } else {
                    System.out.println("cập nhật lịch khám bệnh thất bại");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//
    public void testInitLKB(){
        ArrayList<LichKhamBenh> lichKhamBenhList =new ArrayList<>();
        LichKhamBenh a = new LichKhamBenh("0123456");
        LichKhamBenh b = new LichKhamBenh("0123456231");
        LichKhamBenh c = new LichKhamBenh("01234523216");
        LichKhamBenh d = new LichKhamBenh("0123453216");
        lichKhamBenhList.add(a);
        lichKhamBenhList.add(b);
        lichKhamBenhList.add(c);
        lichKhamBenhList.add(d);
        mDatabase.child("Appointment").setValue(lichKhamBenhList);
    }

    public void getInfo_User(String phone){
        info_User = new User();
        Query query = mDatabase.child("Account")
                .orderByChild("phone")
                .equalTo(phone.trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) { ;
                        UserAccounts temp = user.getValue(UserAccounts.class);
                        info_User = temp.getInfo_user();
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
    }

    public static User getInfo_User() {
        return info_User;
    }

    //    }
}
