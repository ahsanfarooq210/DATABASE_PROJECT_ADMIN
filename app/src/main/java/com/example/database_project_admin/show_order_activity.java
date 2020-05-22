package com.example.database_project_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class show_order_activity extends AppCompatActivity
{
    private SpaceNavigationView navigationView;;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_activity);

        navigationView=findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        //navigationView.addSpaceItem(new SpaceItem("",R.drawable.icons8_delete_file_64px_2));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.icons8_sorting_64px));
        navigationView.setCentreButtonIcon(R.drawable.icons8_product_documents_64px_1);
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.icons8_new_company_80px));
        // navigationView.addSpaceItem(new SpaceItem("",R.drawable.icons8_delete_document_64px));
        navigationView.setCentreButtonSelectable(true);
        navigationView.setCentreButtonSelected();

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener()
        {
            @Override
            public void onCentreButtonClick()
            {

            }

            @Override
            public void onItemClick(int itemIndex, String itemName)
            {

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName)
            {

            }
        });
    }
}
