package com.example.doan_sosuckhoe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new Homeuser();
            case 1:
                return new Reminder();
            case 2:
                return new BMI();
            case 3:
                return new Appointment();
            case 4:
                return new Usermenu();
            default:
                return new Homeuser();

        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
