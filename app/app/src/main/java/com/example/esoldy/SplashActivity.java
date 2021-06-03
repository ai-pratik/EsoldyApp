package com.enfield.esoldy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth=FirebaseAuth.getInstance();



    }

    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser==null)
        {
            Intent registerintent = new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(registerintent);
            finish();
        }
        else
        {
            Intent mainintent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(mainintent);
            finish();
        }
    }
}