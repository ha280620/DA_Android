package com.example.doan_sosuckhoe;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doan_sosuckhoe.Session.SessionManager;
import com.example.doan_sosuckhoe.firebase.realtimedatabase.UserAccountsRequester;

public class Homeuser extends Fragment {
    ImageView tao_qr;
    ImageView cntc, hssk, chisosk, menu, tvbs, datlichkham;
    TextView txt_xinchao;
    ImageView btn_hssk;
    Button btn_dosk;

    UserAccountsRequester userAccountsRequester;

    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homeuser,container,false);

        userAccountsRequester = new UserAccountsRequester(getActivity());
        sessionManager = new SessionManager(getActivity());
        tao_qr= (ImageView) view.findViewById(R.id.tao_qr); ;
        cntc = (ImageView) view.findViewById(R.id.cntc);
        txt_xinchao = (TextView)view.findViewById(R.id.txt_xinchao);
        btn_hssk = (ImageView) view.findViewById(R.id.btn_hssk);
        btn_dosk = (Button)view.findViewById(R.id.dosk);
        chisosk= (ImageView) view.findViewById(R.id.chisosk);
        menu = (ImageView)view.findViewById(R.id.chuyen_menu);
        tvbs = (ImageView)view.findViewById(R.id.tvbs);
        datlichkham = (ImageView) view.findViewById(R.id.datlichkham);

        txt_xinchao.setText("Xin chào "+ sessionManager.getName());

//        userAccountsRequester.testInitLKB();

        hssk = (ImageView) view.findViewById(R.id.hssk);

        tao_qr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), QRCode.class);
                ((Homepage) getActivity()).startActivity(intent);
            }
        });

        datlichkham.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Medicalappointment.class);
                ((Homepage) getActivity()).startActivity(intent);
            }
        });

        cntc.setOnClickListener(new View.OnClickListener() { //khong đuoc
            @Override
            public void onClick(View view) {
                System.out.println("click CNTC");
                Intent intent = new Intent(getActivity(), VacinatedCetification.class);
                startActivity(intent);
            }
        });

        hssk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalInfomation.class);
                ((Homepage) getActivity()).startActivity(intent);
            }
        });

        btn_hssk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalInfomation.class);
                ((Homepage) getActivity()).startActivity(intent);
            }
        });

        btn_dosk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Healthindex.class);
                ((Homepage) getActivity()).startActivity(intent);
            }
        });

        chisosk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Healthindex.class);
                ((Homepage) getActivity()).startActivity(intent);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homepage temp = (Homepage) getActivity();
            }
        });

        tvbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0866039411"));
                startActivity(intent);
            }
        });
        return view;
    }

}
