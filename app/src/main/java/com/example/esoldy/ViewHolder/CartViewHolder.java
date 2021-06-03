package com.example.esoldy.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esoldy.Interface.ItemClickListner;
import com.example.esoldy.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductname,txtproductprice,txtproductQuantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);
        txtproductname=itemView.findViewById(R.id.cart_product_name);
        txtproductprice=itemView.findViewById(R.id.cart_product_price);
        txtproductQuantity=itemView.findViewById(R.id.cart_Product_Quantity);

    }

    @Override
    public void onClick(View v)
    {
        itemClickListner.onClick(v,getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
