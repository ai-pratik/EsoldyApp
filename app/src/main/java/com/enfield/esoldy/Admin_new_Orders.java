package com.enfield.esoldy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.enfield.esoldy.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_new_Orders extends AppCompatActivity {


    private RecyclerView ordersList;
    private DatabaseReference ordersref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new__orders);

        ordersref= FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList=findViewById(R.id.orderlist);
        ordersList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersref,AdminOrders.class)
                .build();
        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder>adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder adminOrdersViewHolder, int i, @NonNull AdminOrders adminOrders) {
                        adminOrdersViewHolder.userName.setText("Name: "+ adminOrders.getName());
                        adminOrdersViewHolder.userPhoneNumber.setText("Phone: "+ adminOrders.getPhone());
                        adminOrdersViewHolder.userTotalPrice.setText("Total Amount: "+ adminOrders.getTotal_Amount()+"₹");
                        adminOrdersViewHolder.userDateTime.setText("Order At: "+ adminOrders.getDate() + " at " +adminOrders.getTime());
                        adminOrdersViewHolder.userShippingaddress.setText("Shipping address: "+ adminOrders.getAddress() + " " +adminOrders.getCity());
                        adminOrdersViewHolder.showorderbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String UID=getRef(i).getKey();
                                Intent intent = new Intent(Admin_new_Orders.this,userorderedproductsadmin.class);
                                intent.putExtra("UID",UID);
                                startActivity(intent);

                            }
                        });

                        adminOrdersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]= new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder= new AlertDialog.Builder(Admin_new_Orders.this);
                                builder.setTitle("Have you Shipped this order Products ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0)
                                        {
                                            String UID=getRef(i).getKey();

                                            Removeorder(UID);

                                        }
                                        else
                                        {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderslayout,parent,false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();

    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName,userPhoneNumber,userTotalPrice,userDateTime,userShippingaddress;
        public Button showorderbutton;

        public AdminOrdersViewHolder(@NonNull View itemView)
        {
            super(itemView);

            userName=itemView.findViewById(R.id.orderusername);
            userPhoneNumber=itemView.findViewById(R.id.orderphonenumber);
            userTotalPrice=itemView.findViewById(R.id.ordertotalprice);
            userDateTime=itemView.findViewById(R.id.orderdatetime);
            userShippingaddress=itemView.findViewById(R.id.orderaddresscity);
            showorderbutton=itemView.findViewById(R.id.ordershowallproducts);
        }
    }


    private void Removeorder(String UID) {

        ordersref.child(UID).removeValue();

    }
}