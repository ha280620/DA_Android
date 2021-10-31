package com.example.doan_sosuckhoe;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doan_sosuckhoe.Session.SessionManager;

public class Usermenu extends Fragment {

    ImageView ttcn, hssk, cntc, cssk;
    TextView dangxuat, xinchao;
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_usermenu,container,false);
        sessionManager = new SessionManager(getContext());
        xinchao = view.findViewById(R.id.xinchao_usermenu);
        dangxuat =view.findViewById(R.id.txt_logout);
        ttcn = view.findViewById(R.id.ttcn);
        hssk = view.findViewById(R.id.hs_sk);
        cntc = view.findViewById(R.id.cn_tc);
        cssk = view.findViewById(R.id.cs_sk);

        xinchao.setText("Xin chào " + sessionManager.getName());
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.clearUserData();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);

            }
        });
        ttcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalInfomation.class);
                startActivity(intent);
            }
        });
        hssk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalInfomation.class);
                startActivity(intent);
            }
        });
        cntc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VacinatedCetification.class);
                startActivity(intent);
            }
        });
        cssk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Healthindex.class);
                startActivity(intent);
            }
        });


        return view;
    }


}
