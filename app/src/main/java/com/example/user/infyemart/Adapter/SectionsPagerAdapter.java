package com.example.user.infyemart.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.infyemart.Fragment_Offers;
import com.example.user.infyemart.Fragment_alert;

public class SectionsPagerAdapter extends FragmentPagerAdapter{
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_alert tab1 = new Fragment_alert();
                return tab1;

            case 1:
                Fragment_Offers tab2 = new Fragment_Offers();
                return tab2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "ALERT";
            case 1:
                return "OFFERS";
        }
        return null;
    }
}
