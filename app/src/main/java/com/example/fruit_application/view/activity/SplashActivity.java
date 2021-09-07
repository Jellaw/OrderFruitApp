package com.example.fruit_application.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fruit_application.R;
import com.example.fruit_application.view.manager.PrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!PrefManager.getInstance(this).isFirstTimeLaunch()) {
            launchHomeScreen();
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                finish();
            }, 1000);
        }
    }
    private void launchHomeScreen() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 1000);
    }
}