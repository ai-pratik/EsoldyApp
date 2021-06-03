package com.example.esoldy.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esoldy.Interface.ItemClickListner;
import com.example.esoldy.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{


    public TextView txtProductname,txtProductdescription,txtProductprice;
    public ImageView imageView;
    public ItemClickListner listner;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.productimage);
        txtProductname=(TextView) itemView.findViewById(R.id.productname);
        txtProductdescription=(TextView) itemView.findViewById(R.id.productdescription);
        txtProductprice=(TextView) itemView.findViewById(R.id.productprice);


    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);

    }
}
