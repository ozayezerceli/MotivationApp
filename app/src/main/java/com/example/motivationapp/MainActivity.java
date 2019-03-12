package com.example.motivationapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.motivationapp.fragment.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        viewPager = findViewById(R.id.main_activity_view_pager);
        tabLayout = findViewById(R.id.main_activity_tab_layout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);





        tabLayout.getTabAt(0).setIcon(R.drawable.application_logo);
        tabLayout.getTabAt(1).setIcon(R.drawable.daily_tab_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.fav_tab_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.reminder_tab_icon);



    }


}
