package com.example.motivationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView appName,slogan;
    private Animation frombottom,fromtop;
    private SharedPreferences sharedPreferences;
    private boolean firstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("mPref",MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("firstTime",true);
        if(firstTime){
            Thread welcomeThread = new Thread() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    firstTime = false;
                    editor.putBoolean("firstTime",firstTime);
                    editor.apply();
                    try {
                        super.run();
                        sleep(3000);
                    } catch (Exception e) {

                    } finally {

                        goToLogin();
                    }
                }
            };
            welcomeThread.start();
        }else{
           goToLogin();
        }

        appName = findViewById(R.id.app_name_textview);
        slogan = findViewById(R.id.app_slogan_textview);
        imageView = findViewById(R.id.imageView);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        appName.setAnimation(frombottom);
        slogan.setAnimation(frombottom);
        imageView.setAnimation(fromtop);


    }
    public void goToLogin(){
        Intent i = new Intent(SplashActivity.this, LoginPage.class);
        startActivity(i);
        finish();
    }
}
