package com.example.doan_sosuckhoe;

import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

public class Register extends AppCompatActivity {

    TextInputLayout txt_phonenumber, txt_fullname, txt_password, txt_confirmpassword;
    Button dangky;
    CheckBox checkBox;
    String phone_number;
    private DatabaseReference mDatabase;
    //private MaterialButton dangky;
    public static boolean validData = true;
    private TextWatcher TextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //gọi database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //ánh xạ
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        txt_phonenumber = findViewById(R.id.phonenumber);
        txt_fullname = findViewById(R.id.txt_fullname);
        txt_password = findViewById(R.id.txt_password);
        txt_confirmpassword = findViewById(R.id.txt_confirmpassword);
        dangky = (Button) findViewById(R.id.btn_dangky);
        //lấy giá trị input


        txt_confirmpassword.getEditText().addTextChangedListener(textWatcher);
        txt_phonenumber.getEditText().addTextChangedListener(textWatcher);
        txt_fullname.getEditText().addTextChangedListener(textWatcher);
        txt_password.getEditText().addTextChangedListener(textWatcher);

        //hàm đăng ký
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nếu đã check được đăng ký không hiển thị thông báo
                if (checkBox.isChecked() == true) {

                    String phone_number = String.valueOf(txt_phonenumber.getEditText().getText());
                    String fullname = txt_fullname.getEditText().getText().toString();
                    String password = txt_password.getEditText().getText().toString();
                    String re_password = txt_confirmpassword.getEditText().getText().toString();
                    String cv_phone = phone_number.substring(1);
                    Intent intent = new Intent(Register.this, VerifyOTP.class);
                    intent.putExtra("phone_number", cv_phone);
                    intent.putExtra("fullname", fullname);
                    intent.putExtra("password", password);
                    intent.putExtra("re_password", re_password);
                    startActivity(intent);
                } else
                    Toast.makeText(Register.this, "Bạn vui lòng đồng ý với điều khoản để tiếp tục thực hiện thủ tục đăng ký.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private TextWatcher textWatcher = new TextWatcher() {

        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            if (txt_phonenumber.getEditText().getText().toString().length()==0) {
                txt_phonenumber.setError("Số điện thoại không được để trống");
                txt_phonenumber.setEnabled(true);
                a = false;
            }
            else if (!txt_phonenumber.getEditText().getText().toString().matches("[0-9]+") || txt_phonenumber.getEditText().getText().length() != 10) {
                a = false;
                txt_phonenumber.setError("Số điện thoại không hợp lệ");
            }
            else{
                Query query = FirebaseDatabase.getInstance().getReference().child("Account").orderByChild("phoneNumber").equalTo(txt_phonenumber.getEditText().getText().toString().trim());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            a = false;
                            txt_phonenumber.setError("Số điện thoại đã được đăng kí");
                            txt_phonenumber.requestFocus();
                        } else {
                            a = true;
                            txt_phonenumber.setError(null);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            if (txt_fullname.getEditText().getText().toString().matches("")) {
                txt_fullname.setError("Họ tên không được để trống");
                b = false;
            } else {
                b = true;
                txt_fullname.setError(null);
            }
            if (txt_password.getEditText().getText().toString().matches("")){
                txt_password.setError("Mật khẩu không được để trống");
                c = false;
            }
            else {
                c = true;

                txt_password.setError(null);
            }
            if (txt_password.getEditText().getText().length() < 6){
                txt_password.setError("Mật khẩu tối thiểu phải 6 kí tự");
                c = false;
            }
            else {
                c = true;
                txt_password.setError(null);
            }
            if (!txt_confirmpassword.getEditText().getText().toString().equals(txt_password.getEditText().getText().toString())){
                txt_confirmpassword.setError("Mật khẩu không trùng khớp");
                d = false;
            }
            else {
                d = true;
                txt_confirmpassword.setError(null);
            }

            if (a && b && c && d){
                dangky.setEnabled(true);
            }
            else {
                dangky.setEnabled(false);

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    void setEnable(boolean result){
        validData = result;
    }
}

