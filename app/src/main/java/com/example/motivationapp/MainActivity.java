package com.example.motivationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.motivationapp.fragment.ViewPagerAdapter;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FirebaseAuth mAuth;
    private Toolbar mToolBar;
    private long backPressedTime ;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-8448457760004752~4372397309");
        mToolBar = findViewById(R.id.activity_main_toolbar);
        viewPager = findViewById(R.id.main_activity_view_pager);
        tabLayout = findViewById(R.id.main_activity_tab_layout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        mAuth = FirebaseAuth.getInstance();
        tabLayout.getTabAt(0).setIcon(R.drawable.logo_tab);
        tabLayout.getTabAt(1).setIcon(R.drawable.daily_tab_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.fav_tab_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.reminder_tab_icon);
        setSupportActionBar(mToolBar);


    }

    @Override
    public void onBackPressed() {
        if(backPressedTime+2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(this, "Çıkmak için geri basınız.", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.options_menu_sign_out){
            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.options_menu_profile){

        }else if (item.getItemId() == R.id.option_menu_help){

        }else if(item.getItemId() == R.id.option_menu_invite){

        }


        return super.onOptionsItemSelected(item);
    }
}
