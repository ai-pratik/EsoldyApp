package com.enfield.esoldy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class Splashactivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashactivity1);

        Intent intent = new Intent(Splashactivity1.this,SplashActivity.class);
        startActivity(intent);
        finish();
    }
}