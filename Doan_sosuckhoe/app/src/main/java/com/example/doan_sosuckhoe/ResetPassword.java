package com.example.doan_sosuckhoe;

import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.concurrent.TimeUnit;

public class ResetPassword extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText edtPhone;
    TextInputLayout txt_new_pass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String TAG = "firebase - REGISTER";
    private EditText edtNum1, edtNum2, edtNum3, edtNum4, edtNum5, edtNum6;
    private Button btnGetOTP, btnVerify;
    TextView tv_resendOTP;
    private String phone_number;
    private String fullname;
    private String password;
    private String re_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone");
        String cv_phone = phone_number.substring(1);

        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPhone.setText(cv_phone);


        tv_resendOTP = findViewById(R.id.tv_resendOTP);
        tv_resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //o day phai truyen cv_phone chu nhiuw ??? c??i n??y t ch??a test ???? t
                sendVerificationCode("+84" + cv_phone);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        edtNum1 = findViewById(R.id.edtNum1);
        edtNum2 = findViewById(R.id.edtNum2);
        edtNum3 = findViewById(R.id.edtNum3);
        edtNum4 = findViewById(R.id.edtNum4);
        edtNum5 = findViewById(R.id.edtNum5);
        edtNum6 = findViewById(R.id.edtNum6);
        btnGetOTP = findViewById(R.id.btnSendOTP);
        btnVerify = findViewById(R.id.btnVerify);

        // set suwj kien sau khi click vao senOTP
        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ResetPassword.this, "Vui l??ng ?????i",Toast.LENGTH_SHORT).show();
                sendVerificationCode("+84" + phone_number);
            }
        });

        //lay ma OTP
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp =
                        edtNum1.getText().toString() +
                                edtNum2.getText().toString() +
                                edtNum3.getText().toString() +
                                edtNum4.getText().toString() +
                                edtNum5.getText().toString() +
                                edtNum6.getText().toString();
                verifyCode(otp);
            }
        });
    }
    //????ng nh???p b???ng s??t
    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // s??? ??i???n tho???i c???n x??c th???c
                        .setTimeout(60L, TimeUnit.SECONDS) //th???i gian timeout
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // callback x??c th???c s??t
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            //H??m n??y ???????c g???i trong hai tr?????ng h???p:
            //1. Trong m???t s??? tr?????ng h???p, ??i???n tho???i di ?????ng ???????c x??c minh t??? ?????ng m?? kh??ng c???n m?? x??c minh.
            //2. Tr??n m???t s??? thi???t b???, c??c d???ch v??? c???a Google Play ph??t hi???n SMS ?????n v?? th???c hi???n quy tr??nh x??c minh m?? kh??ng c???n ng?????i d??ng th???c hi???n b???t k??? h??nh ?????ng n??o.
            Log.d(TAG, "onVerificationCompleted:" + credential);

            //t??? ?????ng ??i???n m?? OTP
            edtNum1.setText(credential.getSmsCode().substring(0,1));
            edtNum2.setText(credential.getSmsCode().substring(1,2));
            edtNum3.setText(credential.getSmsCode().substring(2,3));
            edtNum4.setText(credential.getSmsCode().substring(3,4));
            edtNum5.setText(credential.getSmsCode().substring(4,5));
            edtNum6.setText(credential.getSmsCode().substring(5,6));

            verifyCode(credential.getSmsCode());
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.w(TAG, "onVerificationFailed", e);
            Toast.makeText(ResetPassword.this, "Th???t b???i", Toast.LENGTH_SHORT).show();
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(ResetPassword.this, "y??u c???u th???t b???i", Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                Toast.makeText(ResetPassword.this, "Quota kh??ng ?????", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Log.d(TAG, "onCodeSent:" + verificationId);
            //ShowNotification.dismissProgressDialog();
            Toast.makeText(getApplicationContext(), "???? g???i OTP", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
            mResendToken = token;
        }
    };

    //code x??c th???c OTP
    private void verifyCode(String code) {
        // ShowNotification.showProgressDialog(MainActivity.this, "??ang x??c th???c");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        txt_new_pass = (TextInputLayout)findViewById(R.id.txt_new_password);
                        //ShowNotification.dismissProgressDialog();
                        if (task.isSuccessful()) {
                            //x??c th???c ????? t???o m???t kh???u
                            String pass = txt_new_pass.getEditText().getText().toString();
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(ResetPassword.this, "Ho??n th??nh x??c th???c. Ti???n h??nh c???p nh???t m???t kh???u.", Toast.LENGTH_SHORT).show();
                            //l??u d??? li???u l??n firebase v?? b???t s??? ki???n khi ho??n th??nh valur ?????n ????y n?? ??i???n trong dduaafut ngh?? l?? k???t h???p ?? v???a x??c th???c
                            //r???i l??u mk m??i snene t code c???p nh???t m???t kh???u v??o sau khi x??c th???c th??nh c??ng ti???p theo t h??i b??....
                            //????? t coi l???i, t c??ng k nh??? t code j :vhaha okokasokok t out ah okok
                            Query query = mDatabase.child("Account").orderByChild("phoneNumber").equalTo(phone_number);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                                            String key = user.getKey();
                                            mDatabase.child("Account")
                                                    .child(key).child("password")
                                                    .setValue(pass);
                                            //m???t kh???u nh???p l???i ch??a ???????c c???p nh???t
                                            mDatabase.child("Acccount").child("key").child("rePassword").setValue(pass);
                                            Toast.makeText(ResetPassword.this, "C???p nh???t m???t kh???u th??nh c??ng", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(ResetPassword.this, "Kh??ng th???c hi???n ???????c Qu??n m???t kh???u", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


//                            mDatabase.child("Account").setValue(new Account(fullname, phone_number,password, re_password),
//                                    new DatabaseReference.CompletionListener() {
//                                        @Override
//                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                            if (error == null)
//                                                Toast.makeText(ResetPassword.this, "L??u th??ng tin th??nh c??ng. Vui l??ng x??c th???c s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
//                                            else
//                                                Toast.makeText(ResetPassword.this, "Th??ng tin ch??a ???????c c???p nh???t", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                            Intent intent = new Intent(ResetPassword.this, Homepage.class);
                            startActivity(intent);
                            //ShowNotification.showAlertDialog(MainActivity.this, "Th??nh c??ng");
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                //ShowNotification.showAlertDialog(MainActivity.this, "L???i");
                                Toast.makeText(ResetPassword.this, "X??c th???c th???t b???i. Vui l??ng ki???m tra l???i.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void setupOTPInput() {

        edtNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum3.requestFocus();
                } else {
                    edtNum1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum4.requestFocus();
                } else {
                    edtNum2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum5.requestFocus();
                } else {
                    edtNum3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtNum6.requestFocus();
                } else {
                    edtNum4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNum6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    edtNum5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}

