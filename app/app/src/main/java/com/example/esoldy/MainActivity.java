package com.enfield.esoldy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button sellproductbtn,signout,home;
    private FirebaseUser currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sellproductbtn=findViewById(R.id.sellproductbutton);
        signout=findViewById(R.id.signout);
        home=findViewById(R.id.main_login_btn);
        currentuser=FirebaseAuth.getInstance().getCurrentUser();

       /*home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addnewproductintent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(addnewproductintent);
                finish();
            }
        });*/

        sellproductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addnewproductintent=new Intent(MainActivity.this,Addcategoryadminproduct.class);
                startActivity(addnewproductintent);
                finish();
            }
        });



        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "EXITING THE APP", Toast.LENGTH_SHORT).show();

                deleteAppData();
            }
        });
    }
    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}