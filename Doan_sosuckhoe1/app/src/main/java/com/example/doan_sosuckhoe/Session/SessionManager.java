package com.example.doan_sosuckhoe.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String TAG = SessionManager.class.getName();
    private final SharedPreferences userSession;
    private final SharedPreferences.Editor userDataEditor;
    private final Context mContext;

    public SessionManager(Context context) {
        mContext = context;
        userSession = mContext.getSharedPreferences("userSession", Context.MODE_PRIVATE);
        userDataEditor = userSession.edit();
    }

    public void initUserSession(String phone, String name) {
        userDataEditor.putString("phone", phone);
        userDataEditor.putString("name", name);
        userDataEditor.commit();
    }
    public String getPhone(){
        return  userSession.getString("phone","");
    }
    public String getName(){
        return  userSession.getString("name","");
    }

    public void clearUserData() {
        userDataEditor.clear();
        userDataEditor.commit();
    }
}
