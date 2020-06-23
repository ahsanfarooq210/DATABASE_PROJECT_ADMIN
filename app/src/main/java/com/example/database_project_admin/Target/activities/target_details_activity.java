package com.example.database_project_admin.Target.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.database_project_admin.R;
import com.example.database_project_admin.Target.Fragments.show.SKU_Target_Fragment;
import com.example.database_project_admin.Target.Fragments.show.Target_SalesMen_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class target_details_activity extends AppCompatActivity {

    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_details_activity);
        loadFragment( new SKU_Target_Fragment());
        navView = findViewById(R.id.show_target_nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sku_target:
                        fragment = new SKU_Target_Fragment();
                        //  getSupportFragmentManager().beginTransaction().replace(R.id.add_target_details_frame_layout,new add_target_Fragment()).commit();
                        break;

                    case R.id.salesmen_target:
                        fragment = new Target_SalesMen_Fragment();

                        //getSupportFragmentManager().beginTransaction().replace(R.id.add_target_details_frame_layout,new Edit_fragment_Fragment()).commit();
                        break;
                }
                return loadFragment(fragment);
            }
        });


    } private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.show_target_details_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }




}