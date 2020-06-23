package com.example.database_project_admin.RvAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_project_admin.Entity.SalesmanId;
import com.example.database_project_admin.R;

import java.util.ArrayList;

public class ShowSalesmanRvAdapter extends RecyclerView.Adapter<ShowSalesmanRvAdapter.ViewHolder>
{
    private ArrayList<SalesmanId> salesmanIdArrayList;
    private Activity context;

    public ShowSalesmanRvAdapter(ArrayList<SalesmanId> salesmanIdArrayList, Activity context)
    {
        this.salesmanIdArrayList = salesmanIdArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(context).inflate(R.layout.show_salesman_rv_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.email_tv.setText(salesmanIdArrayList.get(position).getEmail());
        holder.password_tv.setText(salesmanIdArrayList.get(position).getPassword());
    }

    @Override
    public int getItemCount()
    {
        return salesmanIdArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView email_tv,password_tv;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            email_tv=itemView.findViewById(R.id.show_salesman_rv_email);
            password_tv=itemView.findViewById(R.id.show_salesman_rv_password);
        }
    }
}
