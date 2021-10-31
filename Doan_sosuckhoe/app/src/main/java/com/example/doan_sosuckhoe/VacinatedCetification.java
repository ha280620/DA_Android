package com.example.doan_sosuckhoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.Query;

public class VacinatedCetification extends AppCompatActivity {

    ImageView back;
    TextView txt_ht, txt_ns, txt_cmnd;
    private String hoten, ngaysinh, cmnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacinated_cetification);


        back = findViewById(R.id.btn_back);
        txt_cmnd = findViewById(R.id.txt_cmnd);
        txt_ht = findViewById(R.id.txt_ht);
        txt_ns = findViewById(R.id.txt_ns);




        //trở lại
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cho nay homepage hay homuser, tại máy nút back trong các trang acctivity khác đêì là hoem page nên t làm, uh z test thu nha
                Intent intent = new Intent(VacinatedCetification.this, Homepage.class);
                startActivity(intent);
            }
        });
    }
}