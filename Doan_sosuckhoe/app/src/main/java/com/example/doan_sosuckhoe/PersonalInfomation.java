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


        //định dạng ngày sinh
        txt_ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });
        //trở lại
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalInfomation.this, Homepage.class);
                startActivity(intent);
            }
        });
        //hủy bỏ ê mà điền sẵn cho t hỏi 2 vấn đề th,okok 1. t muốn khi load vào á điền thì nó lấy đúng fullname mà lê mạng tìm thấy chy

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonalInfomation.this);
                builder1.setMessage("Bạn có muốn lưu thông tin vừa nhập không?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                builder1.setMessage("Thực hiện nhấn LƯU để cập nhật lại thông tin!");
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

        //lưu
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
//                    //à điền t có ý tưởng nhưng ko biết code, như tnayf, sau khi đăng nhập thành công thì thông tin
//                    //sood điện thoại họ tên đã được lưu trên hệ thống , nên mình sẽ lấy theo cái sốd ddieemnj thoai
//                    //rồi ddiefn các trường còn lại sau khi lưu thì viết code lấy dưc liệ về từ firebase có so sánh số điện thoai
//                    //nhưng t ko biết làm sao laya cái user đang đăng nhập á
//                    //uh y tuong thi tuong doi giong voi y luc nay t noi á, nhưng chỗ điền thông tin thì khỏi cần cập nhật sdt, bỏ qua dc 1 cái kiểm tra
//                    //còn user đăng nhập thì h m dùng cái sharedpreference, lưu tài khoản đang đăng nhập, rồi chỗ nào cần mình cứ thông qua sdt trong shared mà gọi dữ liệu về là dc
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
//                            Toast.makeText(PersonalInfomation.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
//                        }
//
//                        else
//                            Toast.makeText(PersonalInfomation.this, "Vui lòng cập nhật lại thông tin", Toast.LENGTH_SHORT).show();
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
