package com.arctouch.codechallenge.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.home.HomeActivity;
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
