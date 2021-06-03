package com.example.esoldy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent registerintent = new Intent(SplashActivity.this, testloginactivity
                .class);
        startActivity(registerintent);
        finish();

        //firebaseAuth = FirebaseAuth.getInstance();


    }

}