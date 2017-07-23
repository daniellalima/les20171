package com.example.android.espacod.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import com.example.android.espacod.R;
import com.example.android.espacod.view.MainActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 2000);
    }
}