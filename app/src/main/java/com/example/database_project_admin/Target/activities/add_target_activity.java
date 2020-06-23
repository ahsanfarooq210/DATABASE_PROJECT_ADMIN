package com.example.database_project_admin.Target.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.database_project_admin.R;
import com.example.database_project_admin.Target.Fragments.Add.Edit_fragment_Fragment;
import com.example.database_project_admin.Target.Fragments.Add.add_target_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class add_target_activity extends AppCompatActivity {


    // BottomNavigationView
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_target_activity);
        //loading the default fragment
        loadFragment( new add_target_Fragment());
        navView = findViewById(R.id.add_target_nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_target:
                        fragment = new add_target_Fragment();
                      //  getSupportFragmentManager().beginTransaction().replace(R.id.add_target_details_frame_layout,new add_target_Fragment()).commit();
                        break;

                    case R.id.edit_target:
                        fragment = new Edit_fragment_Fragment();

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
                    .replace(R.id.add_target_details_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}