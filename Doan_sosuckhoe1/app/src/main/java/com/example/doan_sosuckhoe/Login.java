package com.example.doan_sosuckhoe;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_sosuckhoe.Session.SessionManager;
import com.example.doan_sosuckhoe.model.UserAccounts;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView tv_signin, tv_resetPass ;
    Button btn_dangnhap;
    EditText txt_username;
    TextInputLayout txt_password;
    String phone, passTxt;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);
        tv_signin = (TextView) findViewById(R.id.tv_signin);
        tv_resetPass =(TextView) findViewById(R.id.tv_resetPass);
        btn_dangnhap = (Button) findViewById(R.id.btn_dangnhap);
        txt_username = (EditText)findViewById(R.id.txt_username);
        txt_password = (TextInputLayout)findViewById(R.id.txt_password);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // dang ky
        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
//        đọc dữ liêu để so sánh thông tin đăng nhập
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = txt_username.getText().toString();
                passTxt = txt_password.getEditText().getText().toString();
                //lấy danh sách các child bên trong node "Account" à điền như cái mã tự sinh á nó ko ảnh hưởn gì mình ko cần gọi đến nó hả,
//                có ảnh hưởng, cần dùng nó để cập nhật dữ liệu của 1 tài khoản đã có á còn như mình gọi thì ko cần qua nó hả điền
//                chỗ oderChild là sắp xếp các child bên trong Account theo cột số điện thoại, là thay vì mình có danh sách đó hiện tại nó xếp theo mã tự sinh á, giờ mình xếp theo sdtaf okok t quá hiểu r, okok,
//                còn chỗ equal phone thì kiểm tra sdt, nó trả về 1 danh sách các child có trùng sdt mình cần tìm, ở đây thì chỉ có 1 à, mà tại k có cách truy vấn khác nên phải lấy cả list
                // à là phone sẽ bằng với sđt input user nhập sau đó mình ss phone với list đó coi có ko,
                //uh, cácisnapshot là nó chứa dữ liệu, còn query đại loại lắng nghe sự thay đổi về dữ liệu trên firebase á, này t hiểu z chứ đúng thì t k chắc lắm :v
                //à ok t hiểu ý m rồi à điền mấy chỗ m giải thích t hiểu r,okok
                System.out.println("so dien thoai"+phone);
                Query query = mDatabase.child("Account").orderByChild("phone").equalTo(phone);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot user : snapshot.getChildren()){
                               UserAccounts account = user.getValue(UserAccounts.class);
                                if (account.password.equals(passTxt)){
                                    String fullName = account.fullName;
                                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Homepage.class);
                                    sessionManager.initUserSession(account.phone,account.fullName);

//                                    intent.putExtra("phone", phone);
//                                    intent.putExtra("fullName", fullName);//truyền name đến homeusser nhưng khỏ là homeusser lại là fragment m gặp case này chwua,hình như rồi mà có 1 lần th :v, để t coi lại ok giúp r
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this, "Tài khoản sai mật khẩu. Vui lòng kiểm tra mật khẩu!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(Login.this, "Tài khoản không tồn tại. Vui lòng kiểm tra số điện thoại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //mà quên mật khẩu chứ m ừ là t kiểm tra xem sđt đó có trên hệ thống mới cho đổi,uh z đúng mà
        tv_resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = txt_username.getText().toString();
                passTxt = txt_password.getEditText().getText().toString();
                //nhập mã xác nhân
                //tài khoản đã được đăng ký
                Query  query = mDatabase.child("Account").orderByChild("phoneNumber").equalTo(phone);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            Intent intent = new Intent(Login.this, ResetPassword.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                            //cap nhat mat khau ê
                        }
                        else
                            Toast.makeText(Login.this, "Tài khoản không tồn tại. Không thực hiện chức năng Quên mật khẩu.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(Login.this, "Vui lòng nhập số điện thoại để xác thực số điện thoại.", Toast.LENGTH_LONG).show();
                //đổi mật khẩu
            }
        });
    }
}