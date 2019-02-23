package com.example.motivationapp;

import android.support.annotation.Nullable;
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
               selectedFragment = DailySentences.newInstance();
               break;
           case 1:
               selectedFragment = Favourite.newInstance();
               break;
           case 2:
               selectedFragment = NotificationReminder.newInstance();
               break;
               default:
                   return null;
       }
        return selectedFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence selectedTitle;

        switch (position){
            case 0:
                selectedTitle = "Daily Sentence";
                break;
            case 1:
                selectedTitle = "Favourites";
                break;
            case 2:
                selectedTitle = "Reminder";
                break;
                default:
                    return null;
        }
        return selectedTitle;
    }
}
