package com.app.practicaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        TextView tv_practical = findViewById(R.id.tv_practical);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_in);
        tv_practical.startAnimation(animFadeIn);

        if (!SharedPref.getBooleanPref(SplashActivity.this, "KEY_FIRST_INSTALL")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            },3000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            },3000);        }


    }
}