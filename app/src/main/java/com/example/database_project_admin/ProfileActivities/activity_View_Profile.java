package com.example.database_project_admin.ProfileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.database_project_admin.R;
import com.example.database_project_admin.Activities.admin_main_dashboard;

public class activity_View_Profile extends AppCompatActivity {
 TextView Name_textView,Cnic_textView,Email_textView,dob_textView,cell_no_textView,education_textView;
 Button ok_button,edit_Profile_button;
    //splash screen relative layout
    private RelativeLayout rellay1, rally3, rellay2;
    //handler for the splash screen
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {

            rellay1.setVisibility(View.VISIBLE);
            rally3.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);

        }
    };

    //handler for the progress bar
    private ProgressBar progressBar;
    private Handler progressBarh = new Handler();
    private Runnable runnable1 = new Runnable()
    {
        @Override
        public void run()
        {

            progressBar.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view__profile);
        Name_textView=findViewById(R.id.Name_textView);
        Cnic_textView=findViewById(R.id.Cnic_textView);
        Email_textView=findViewById(R.id.Email_textView);
        dob_textView=findViewById(R.id.dob_textView);
        cell_no_textView=findViewById(R.id.cell_no_textView);
        education_textView=findViewById(R.id.education_textView);

        Name_textView.setText("Sales Men");

        Cnic_textView.setText("33333-1234567-8");

        Email_textView.setText("salesmen2@gmail.com");

        dob_textView.setText("29/02/1996");

        education_textView.setText("BSCS");
        //relative layouts
        rellay1 = findViewById(R.id.adding_sku_rellay1);
        rellay2 = findViewById(R.id.adding_sku_rellay2);
        rally3 = findViewById(R.id.adding_sku_bottom_rally2);

        //splash screen
        handler.postDelayed(runnable, 500);

        //progress bar
        progressBar = findViewById(R.id.my_progress_bar);
        progressBarh.postDelayed(runnable1, 0);
        ok_button=findViewById(R.id.ok_button);
        edit_Profile_button=findViewById(R.id.edit_Profile_button);
         ok_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent send =new Intent(activity_View_Profile.this, admin_main_dashboard.class);
                startActivity(send);
             }
         });
         edit_Profile_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent send =new Intent(activity_View_Profile.this, activity_Edit_Profile.class);
                 startActivity(send);
             }
         });
    }
}