package com.example.corruptionperceptionindex.src.screens.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.screens.intro.IntroActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                finish();
            }
        }, 1000);
    }
}