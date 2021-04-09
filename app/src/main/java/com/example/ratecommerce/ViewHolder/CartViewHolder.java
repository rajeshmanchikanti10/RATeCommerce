package com.example.ratecommerce.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratecommerce.Interface.ItemClickListener;
import com.example.ratecommerce.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView TextproductName,TextProductPrice,TextProductQuantity;
    private ItemClickListener itemClickListener;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        TextproductName=itemView.findViewById(R.id.cart_Product_name);
        TextProductPrice=itemView.findViewById(R.id.cart_Product_price);
        TextProductQuantity=itemView.findViewById(R.id.cart_product_quantity);

    }

    @Override
    public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }
}
