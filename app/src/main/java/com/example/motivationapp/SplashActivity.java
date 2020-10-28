package com.example.motivationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView appName,slogan;
    private Animation frombottom,fromtop;
    private int SLEEP_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String key =intent.getStringExtra("signUp");
        if(key!=null && key.matches("1")){
            SLEEP_TIME = 8000;
            Toast.makeText(this, "Motive Olmaya Hazır Olun!!!", Toast.LENGTH_SHORT).show();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
            Thread welcomeThread = new Thread() {
                @Override
                public void run() {
                    try {
                        super.run();
                        sleep(SLEEP_TIME);
                    } catch (Exception e) {

                    } finally {
                        goToLogin();
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
    public void goToLogin(){
        Intent i = new Intent(SplashActivity.this, LoginPage.class);
        startActivity(i);
        finish();
    }


}
