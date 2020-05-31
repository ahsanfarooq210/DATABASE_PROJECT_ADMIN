package com.example.database_project_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class admin_main_dashboard extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_dashboard);
    }

    public void addSalesman(View view)
    {
        startActivity(new Intent(admin_main_dashboard.this,add_salesman_activity.class));
    }

    public void showOrder(View view)
    {
        startActivity(new Intent(admin_main_dashboard.this,show_order_activity.class));
    }
}
