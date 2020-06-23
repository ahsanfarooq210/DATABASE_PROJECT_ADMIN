package com.example.database_project_admin.RvAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_project_admin.Entity.Orders;
import com.example.database_project_admin.R;

import java.util.ArrayList;

public class show_order_rv_adaprter extends RecyclerView.Adapter<show_order_rv_adaprter.viewHolder>
{
    private ArrayList<Orders> shop;
    private Activity context;

    public show_order_rv_adaprter(ArrayList<Orders> shop, Activity context)
    {
        this.shop = shop;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(context).inflate(R.layout.show_orders_rv_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position)
    {
        holder.shopName.setText(shop.get(position).getShopName());
        holder.skuName.setText(shop.get(position).getSku().toString());
        holder.quantity.setText(Integer.toString(shop.get(position).getQuantity()));

    }

    @Override
    public int getItemCount()
    {
        return shop.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView shopName,skuName,quantity;
        public viewHolder(@NonNull View itemView)
        {
            super(itemView);
            shopName=itemView.findViewById(R.id.show_order_rv_layout_shopname);
            skuName=itemView.findViewById(R.id.show_order_rv_layout_skuname);
            quantity=itemView.findViewById(R.id.show_order_rv_layout_quantity);
        }
    }
}
