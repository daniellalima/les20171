package com.example.android.espacod.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.example.android.espacod.R;

public class SplashActivity extends Activity {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    @Override
    public void onResume() {
        super.onResume();

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if (mPreferences.contains("AuthToken")) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        }, 2000);
    }
}