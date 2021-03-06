package com.example.doan_sosuckhoe;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_sosuckhoe.Session.SessionManager;
import com.example.doan_sosuckhoe.firebase.realtimedatabase.UserAccountsRequester;
import com.example.doan_sosuckhoe.model.User;
import com.example.doan_sosuckhoe.model.UserAccounts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PersonalInfomation extends AppCompatActivity {

    SessionManager sessionManager;
    UserAccountsRequester userAccountsRequester;
    private DatabaseReference mDatabase;
    EditText txt_hoten, txt_ngaysinh, txt_phone, txt_cmnd, txt_msbh, txt_diachi, txt_email;
    EditText txt_nhommau, txt_cannang, txt_chieucao, txt_tieusu;
    ImageView back;
    CheckBox nam,nu;
    Button btn_save, btn_cancel;

    private String hoten, ngaysinh, gioitinh, phone, msbh, cmnd, diachi, email, anh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infomation);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sessionManager = new SessionManager(this);

        userAccountsRequester = new UserAccountsRequester(this);
        userAccountsRequester.getInfo_User(sessionManager.getPhone());




        back = (ImageView)findViewById(R.id.back);
        txt_hoten = findViewById(R.id.txt_hoten);
        txt_hoten.setText(sessionManager.getName());

        txt_ngaysinh = findViewById(R.id.txt_ngaysinh);
        nam = (CheckBox) findViewById(R.id.nam);
        nu  = (CheckBox) findViewById(R.id.nu);

        txt_phone = findViewById(R.id.txt_sdt);
        txt_phone.setText(sessionManager.getPhone());


        txt_msbh = findViewById(R.id.txt_msbh);



        txt_cmnd = findViewById(R.id.txt_cmnd);


        txt_diachi = findViewById(R.id.txt_diachi);

        txt_email = findViewById(R.id.txt_email);

        txt_nhommau = findViewById(R.id.txt_nhommau);
        txt_cannang = findViewById(R.id.txt_cannang);
        txt_chieucao = findViewById(R.id.txt_chieucao);
        txt_tieusu = findViewById(R.id.txt_tieusu);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        Query query = mDatabase.child("Account")
                .orderByChild("phone")
                .equalTo(sessionManager.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User temp = new User();
                    for (DataSnapshot user : dataSnapshot.getChildren()) { ;
                        UserAccounts tempAcc = user.getValue(UserAccounts.class);
                        temp = tempAcc.getInfo_user();
                        txt_msbh.setText(temp.MaBHYT);
                        txt_cmnd.setText(temp.CMND);
                        txt_diachi.setText(temp.DiaChi);
                        txt_email.setText(temp.email);
                        System.out.println("c???p nh???t h??? s?? th??nh c??ng");
                    }
                } else {
                    System.out.println("c???p nh???t h??? s?? th???t b???i");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //?????nh d???ng ng??y sinh
        txt_ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });
        //tr??? l???i
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalInfomation.this, Homepage.class);
                startActivity(intent);
            }
        });
        //h???y b??? ?? m?? ??i???n s???n cho t h???i 2 v???n ????? th,okok 1. t mu???n khi load v??o ?? ??i???n th?? n?? l???y ????ng fullname m?? l?? m???ng t??m th???y chy

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonalInfomation.this);
                builder1.setMessage("B???n c?? mu???n l??u th??ng tin v???a nh???p kh??ng?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                builder1.setMessage("Th???c hi???n nh???n L??U ????? c???p nh???t l???i th??ng tin!");
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(PersonalInfomation.this, Homepage.class);
                                startActivity(intent);
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        //l??u
        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hoten = txt_hoten.getText().toString();
                ngaysinh = txt_ngaysinh.getText().toString();
                if (nam.isChecked() == true)
                    gioitinh = "nam";
                else
                    gioitinh = "nu";
                phone = txt_phone.getText().toString();
                msbh = txt_msbh.getText().toString();
                cmnd = txt_cmnd.getText().toString();
                diachi = txt_diachi.getText().toString();
                email = txt_email.getText().toString();

                User tempUser = new User();
                tempUser.MaBHYT = msbh;
                tempUser.CMND = cmnd;
                tempUser.DiaChi = diachi;
                tempUser.email = email;
                userAccountsRequester.updateUserAccount(sessionManager.getPhone(), tempUser);

                //
//                mDatabase.child("User").push().setValue(new User(hoten, ngaysinh, gioitinh, phone, msbh, cmnd, diachi, email),
//                        new DatabaseReference.CompletionListener() {
//                    @Override
//                    //?? ??i???n t c?? ?? t?????ng nh??ng ko bi???t code, nh?? tnayf, sau khi ????ng nh???p th??nh c??ng th?? th??ng tin
//                    //sood ??i???n tho???i h??? t??n ???? ???????c l??u tr??n h??? th???ng , n??n m??nh s??? l???y theo c??i s???d ddieemnj thoai
//                    //r???i ddiefn c??c tr?????ng c??n l???i sau khi l??u th?? vi???t code l???y d??c li??? v??? t??? firebase c?? so s??nh s??? ??i???n thoai
//                    //nh??ng t ko bi???t l??m sao laya c??i user ??ang ????ng nh???p ??
//                    //uh y tuong thi tuong doi giong voi y luc nay t noi ??, nh??ng ch??? ??i???n th??ng tin th?? kh???i c???n c???p nh???t sdt, b??? qua dc 1 c??i ki???m tra
//                    //c??n user ????ng nh???p th?? h m d??ng c??i sharedpreference, l??u t??i kho???n ??ang ????ng nh???p, r???i ch??? n??o c???n m??nh c??? th??ng qua sdt trong shared m?? g???i d??? li???u v??? l?? dc
//                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                        if (error == null)
//                        {
//                            txt_hoten.setText(hoten);
//                            txt_ngaysinh.setText(ngaysinh);
//                            txt_diachi.setText(diachi);
//                            txt_cmnd.setText(cmnd);
//                            txt_email.setText(email);
//                            txt_msbh.setText(msbh);
//                            txt_phone.setText(phone);
//                            Toast.makeText(PersonalInfomation.this, "C???p nh???t th??ng tin th??nh c??ng", Toast.LENGTH_SHORT).show();
//                        }
//
//                        else
//                            Toast.makeText(PersonalInfomation.this, "Vui l??ng c???p nh???t l???i th??ng tin", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

    }
    public void ChonNgay()
    {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txt_ngaysinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
}
