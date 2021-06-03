package com.enfield.esoldy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enfield.esoldy.Model.Cart;
import com.enfield.esoldy.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userorderedproductsadmin extends AppCompatActivity {

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartlistRef;

    private String userID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userorderedproductsadmin);

        userID=getIntent().getStringExtra("UID");
        productsList=findViewById(R.id.productslist);
        productsList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);




        cartlistRef= FirebaseDatabase.getInstance().getReference()
                .child("Cart List")
                .child("Seller View")
                .child(userID).child("Products");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartlistRef,Cart.class)
                .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.txtproductQuantity.setText("Quantity = "+ cart.getQuantity());
                cartViewHolder.txtproductprice.setText("Price "+ cart.getPrice_INR() +"₹");
                cartViewHolder.txtproductname.setText(cart.getProduct_name());

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };


        productsList.setAdapter(adapter);
        adapter.startListening();
    }
}