package com.example.esoldy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esoldy.Model.Cart;
import com.example.esoldy.Model.Products;
import com.example.esoldy.Prevalent.Prevalent;
import com.example.esoldy.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cart_activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button Nextprocessbtn;
    private TextView txtTotalamount,txtmsg1;
    private int overTotalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        recyclerView=findViewById(R.id.cartlist);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Nextprocessbtn=findViewById(R.id.nextprocessbtn);
        txtTotalamount=findViewById(R.id.totalPrice);
        txtmsg1=findViewById(R.id.msg1);


        Nextprocessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTotalamount.setText("Total Price =  "+ String.valueOf(overTotalPrice));

                Intent intent=new Intent(Cart_activity.this,confirmFinalOrderActivity.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Checkorderstate();
        //txtTotalamount.setText("Total Price =  "+ String.valueOf(overTotalPrice));


        final DatabaseReference cartlistRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartlistRef.child("User View")
                        .child(Prevalent.OnlineUser.getPhone())
                        .child("Products"),Cart.class)
                        .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.txtproductQuantity.setText("Quantity = "+ cart.getQuantity());
                cartViewHolder.txtproductprice.setText("Price "+ cart.getPrice_INR() +"₹");
                cartViewHolder.txtproductname.setText(cart.getProduct_name());

                int onetypeProductTprice=((Integer.valueOf(cart.getPrice_INR()))) * Integer.valueOf(cart.getQuantity());
                overTotalPrice=overTotalPrice + onetypeProductTprice;



                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(Cart_activity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    if(which==0)
                                    {
                                        Intent intent =new Intent(Cart_activity.this,productDetailsActivity.class);
                                        intent.putExtra("PID",cart.getPID());
                                        startActivity(intent);


                                    }
                                    if(which==1)
                                    {
                                        cartlistRef.child("User View")
                                                .child(Prevalent.OnlineUser.getPhone())
                                                .child("Products")
                                                .child(cart.getPID())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(Cart_activity.this, "Item Removed Sucessfully", Toast.LENGTH_SHORT).show();
                                                            Intent intent =new Intent(Cart_activity.this,NavgiationhomeActivity.class);
                                                            startActivity(intent);

                                                        }
                                                    }
                                                });
                                    }
                            }
                        });
                        builder.show();
                    }
                });

                /*cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options()
                    }
                });*/

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
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
                    String Username=snapshot.child("name").getValue().toString();
                    if(shippingState.equals("Shipped"))
                    {
                        txtTotalamount.setText("Dear "+ Username +"\n Order is Shipped sucessfully.");
                        recyclerView.setVisibility(View.GONE);

                        txtmsg1.setVisibility(View.VISIBLE);
                        txtmsg1.setText("Congratulations Your Final order has been Shipped successfully,soon you recieved your order at your doarstep");
                        Nextprocessbtn.setVisibility(View.GONE);

                        Toast.makeText(Cart_activity.this, "You can purchased more products once you recieved your First Orders", Toast.LENGTH_SHORT).show();

                    }
                    else if(shippingState.equals("Not Shipped"))
                    {
                        txtTotalamount.setText("Shipping State = Not shipped");
                        recyclerView.setVisibility(View.GONE);

                        txtmsg1.setVisibility(View.VISIBLE);
                        Nextprocessbtn.setVisibility(View.GONE);

                        Toast.makeText(Cart_activity.this, "You can purchased more products once you recieved your First Orders", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}