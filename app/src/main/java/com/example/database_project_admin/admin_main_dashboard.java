package com.example.database_project_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ScrollView;

public class admin_main_dashboard extends AppCompatActivity
{
    private ScrollView scrollView;
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            scrollView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_dashboard);
        scrollView=findViewById(R.id.dashboard_scroll_view);
        handler.postDelayed(runnable,1000);
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
