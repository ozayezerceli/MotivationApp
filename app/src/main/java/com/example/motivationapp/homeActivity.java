package com.example.motivationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class homeActivity extends AppCompatActivity {
    ImageView imageView;
    TextView appName,slogan;
    Animation frombottom,fromtop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(homeActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
        appName = findViewById(R.id.app_name_textview);
        slogan = findViewById(R.id.app_slogan_textview);
        imageView = findViewById(R.id.imageView);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        appName.setAnimation(frombottom);
        slogan.setAnimation(frombottom);
        imageView.setAnimation(fromtop);


    }
}
