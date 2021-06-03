package com.enfield.esoldy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.enfield.esoldy.Model.Products;
import com.enfield.esoldy.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productDetailsActivity extends AppCompatActivity {

    //private FloatingActionButton addtocartbtn;
    private Button addtocart;
    private ImageView productimage;
    private ElegantNumberButton numberButton;
    private TextView productname,productDescription,productPrice,sellerphone;
    private String productID="",state="Normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //addtocartbtn=findViewById(R.id.add_product_to_cart);
        numberButton=findViewById(R.id.number_btn);
        productimage=findViewById(R.id.product_image_details);
        productname=findViewById(R.id.product_name);
        productDescription=findViewById(R.id.product_description1);
        productPrice=findViewById(R.id.product_price1);
        addtocart=findViewById(R.id.addtocart_productdetail);
        //sellerphone=findViewById(R.id.sellerphone);

        productID=getIntent().getStringExtra("PID");


        getproductdetails(productID);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state.equals("Order Placed")||state.equals("Order Shipped"))
                {
                    Toast.makeText(productDetailsActivity.this, "You can purchase more products,once your order is shipped or confirmed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addingcartlist();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        Checkorderstate();
    }

    private void addingcartlist() {

        String savecurrenttime,savecurrentdate;
        Calendar calfordate= Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss");
        savecurrenttime=currenttime.format(calfordate.getTime());

        final DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartmap=new HashMap<>();
        cartmap.put("PID",productID);
        cartmap.put("Product_name",productname.getText().toString());
        cartmap.put("Price_INR",productPrice.getText().toString());
        cartmap.put("Date",savecurrentdate);
        cartmap.put("Time",savecurrenttime);
        cartmap.put("Quantity",numberButton.getNumber());
        cartmap.put("Discount","");
        cartlistref.child("User View").child(Prevalent.OnlineUser.getPhone())
                .child("Products")
                .child(productID)
                .updateChildren(cartmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            cartlistref.child("Seller View").child(Prevalent.OnlineUser.getPhone())
                                    .child("Products")
                                    .child(productID)
                                    .updateChildren(cartmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(productDetailsActivity.this, "Added To cart", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(productDetailsActivity.this,NavgiationhomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                        }
                                    });
                        }
                    }
                });


    }

    private void getproductdetails(String productID) {

        DatabaseReference productsref= FirebaseDatabase.getInstance().getReference().child("Products");
        productsref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);
                    productname.setText(products.getProduct_Name());
                    productPrice.setText(products.getPrice_INR());
                    productDescription.setText(products.getDescription());
                    //sellerphone.setText(products.getSeller_phone());
                    Picasso.get().load(products.getImage()).into(productimage);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Checkorderstate()
    {
        DatabaseReference ordersRef;
        ordersRef=FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.OnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String shippingState=snapshot.child("State").getValue().toString();
                    //String Username=snapshot.child("name").getValue().toString();
                    if(shippingState.equals("Shipped"))
                    {
                        state="Order Shipped";

                    }
                    else if(shippingState.equals("Not Shipped"))
                    {
                        state="Order Placed";
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}