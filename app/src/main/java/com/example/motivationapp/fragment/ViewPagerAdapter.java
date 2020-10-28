package com.example.motivationapp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
       Fragment selectedFragment;

       switch (i){
           case 0:
               selectedFragment = AllMotivationalSentences.newInstance();
               break;
           case 1:
               selectedFragment = DailySentences.newInstance();
               break;
           case 2:
               selectedFragment = Favourite.newInstance();
               break;
           case 3:
               selectedFragment = NotificationReminder.newInstance();
               break;
               default:
                   return null;
       }
        return selectedFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }



}
