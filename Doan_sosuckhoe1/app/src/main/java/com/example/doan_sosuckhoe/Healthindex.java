package com.example.doan_sosuckhoe;


import static java.lang.Float.isNaN;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Healthindex extends AppCompatActivity {

    ImageView back;
    Button btnCheckNow, btn_tuvanbs;
    EditText edtWeight, edtHeight, edtSystolic, edtDiastolic;
    TextView txtHS;

    String BloodP, BMIResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthindex);


        //chức năng đo sức khỏe

        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        edtSystolic = findViewById(R.id.edtSystolic);
        edtDiastolic = findViewById(R.id.edtDiastolic);
        btnCheckNow = findViewById(R.id.btnCheckNow);
        txtHS = findViewById(R.id.HealthCalResult);
        btn_tuvanbs = findViewById(R.id.btn_tuvanbs);
        back = (ImageView)findViewById(R.id.trolai);



        btnCheckNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String height = edtHeight.getText().toString();
                String weight = edtWeight.getText().toString();
                String systolic = edtSystolic.getText().toString();
                String diastolic = edtDiastolic.getText().toString();

                float wValue = Float.parseFloat(weight);
                float hValue = Float.parseFloat(height)/100;
                float sValue = Float.parseFloat(systolic);
                float dValue = Float.parseFloat(diastolic);

                float bmi = wValue / (hValue * hValue);

                if (bmi < 16) {
                    BMIResult = "Suy dinh dưỡng nặng";
                } else if (bmi < 18.5) {
                    BMIResult = "Thiếu Cân";
                } else if (bmi > 18.5 && bmi <= 24.9) {
                    BMIResult = "Bình Thường";
                } else if (bmi >= 25 && bmi <= 29.9) {
                    BMIResult = "bị Thừa Cân";
                } else if (bmi >= 30 && bmi <= 34.9) {
                    BMIResult = "bị Béo Phì Độ I";
                } else if (bmi >= 35 && bmi <= 39.9) {
                    BMIResult = "bị Béo Phì Độ II";
                } else {
                    BMIResult = "bị Béo Phì Độ III";
                }

                if (isNaN(sValue) || isNaN(dValue)) {
                    BloodP = "hãy vui lòng nhập số";
                } else if (sValue <= 60 || dValue <= 40) {
                    BloodP = "hãy làm ơn nhập đúng con số";
                } else if (sValue < 90 || dValue < 60) {
                    BloodP = " bị Huyết áp thấp";
                } else if (sValue < 120 && dValue < 80) {
                    BloodP = "có Huyết áp bình thường tối ưu";
                } else if (sValue <= 120 || dValue >= 80) {
                    BloodP = "có Huyết Áp Bình Thường";
                } else if (sValue <= 130 || dValue >= 85) {
                    BloodP = "có Huyết áp bình thường cao";
                } else if (sValue <= 140 || dValue >= 90) {
                    BloodP = "bị Tăng huyết áp độ 1";
                } else if (sValue <= 160 || dValue >= 100) {
                    BloodP = "bị Tăng huyết áp độ 2";
                } else if (sValue >= 180 || dValue >= 110) {
                    BloodP = "bị Tăng huyết áp độ 3";
                } else {
                    BloodP = "bị Tăng Huyết Áp";
                }

                txtHS.setText("Chỉ số sức khỏe của bạn là:\n" + "- BMI: " + bmi + " - Bạn " + BMIResult + "\n- Huyết Áp: " + sValue + "/" + dValue + " - Bạn " + BloodP);
            }
        });
        btn_tuvanbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0866039411"));
                startActivity(intent);
            }
        });

        //trở lại
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Healthindex.this, Homepage.class);
                startActivity(intent);
            }
        });
    }
}