package com.example.esoldy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class beforeaddcategoryproduct extends AppCompatActivity {

    private Button Nexttocategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforeaddcategoryproduct);
        Nexttocategory=findViewById(R.id.beforecategorybutton);

        Nexttocategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nexttocategory=new Intent(beforeaddcategoryproduct.this,Addcategoryadminproduct.class);
                startActivity(nexttocategory);

            }
        });
    }
}