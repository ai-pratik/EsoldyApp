package com.example.esoldy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esoldy.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class confirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEdittext,phoneEdittext,addressEditText,cityEditText;
    private Button confirmOrderbtn;

    private String totalAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount= getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = " + totalAmount, Toast.LENGTH_SHORT).show();

        confirmOrderbtn=findViewById(R.id.confirm_final_order);
        nameEdittext=findViewById(R.id.shipmentPersonName);
        phoneEdittext=findViewById(R.id.shipmentphonenumber);
        addressEditText=findViewById(R.id.shipmentAddress);
        cityEditText=findViewById(R.id.shipmentCity);

        confirmOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }

    private void Check()
    {
        if(TextUtils.isEmpty(nameEdittext.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your Full Name ", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phoneEdittext.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your Phone number ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your Address ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your Address ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            confirmOrder();
        }
    }

    private void confirmOrder()
    {
        final String savecurrentdate,savecurrenttime;
        Calendar calfordate= Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss");
        savecurrenttime=currenttime.format(calfordate.getTime());

        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.OnlineUser.getPhone());

        HashMap<String,Object> ordersMap= new HashMap<>();
        ordersMap.put("Total_Amount",totalAmount);
        ordersMap.put("name",nameEdittext.getText().toString());
        ordersMap.put("phone",phoneEdittext.getText().toString());
        ordersMap.put("address",addressEditText.getText().toString());
        ordersMap.put("city",cityEditText.getText().toString());
        ordersMap.put("Date",savecurrentdate);
        ordersMap.put("Time",savecurrenttime);
        ordersMap.put("State","Not Shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.OnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful())
                                   {
                                       Toast.makeText(confirmFinalOrderActivity.this, "Your Order Has been placed Sucessfully", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(confirmFinalOrderActivity.this,NavgiationhomeActivity.class);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(intent);
                                       finish();
                                   }
                                }
                            });
                }
            }
        });



    }
}