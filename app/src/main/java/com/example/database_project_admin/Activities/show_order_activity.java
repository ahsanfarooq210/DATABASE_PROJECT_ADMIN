package com.example.database_project_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.database_project_admin.Fragments.*;
import com.example.database_project_admin.R;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class show_order_activity extends AppCompatActivity
{
    private SpaceNavigationView navigationView;
    private show_order_filter_salesman showOrderFilterSalesman;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_activity);

        navigationView=findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("",R.drawable.icons8_category_80px));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.icons8_product_documents_128px));
        navigationView.setCentreButtonIcon(R.drawable.icons8_manager_52px);
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.icons8_online_store_128px_1));
        navigationView.addSpaceItem(new SpaceItem("",R.drawable.icons8_company_50px));
        navigationView.setCentreButtonSelectable(true);
        navigationView.setCentreButtonSelected();
        //initializing the fragments
        showOrderFilterSalesman=new show_order_filter_salesman();

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener()
        {
            @Override
            public void onCentreButtonClick()
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.show_order_filter_framelayout,showOrderFilterSalesman).commit();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName)
            {
                if(itemIndex==0)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.show_order_filter_framelayout,new show_order_filter_catagory()).commit();
                }
                else
                if(itemIndex==1)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.show_order_filter_framelayout,new show_order_filter_sku()).commit();
                }
                else
                {
                    if(itemIndex==2)
                    {
                        getSupportFragmentManager().beginTransaction().replace(R.id.show_order_filter_framelayout,new show_order_filter_shop()).commit();
                    }
                    else
                    {
                       if(itemIndex==3)
                       {
                            getSupportFragmentManager().beginTransaction().replace(R.id.show_order_filter_framelayout,new show_order_filter_company()).commit();
                       }
                    }
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName)
            {

            }
        });
    }
}
