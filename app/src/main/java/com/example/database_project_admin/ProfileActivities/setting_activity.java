package com.example.database_project_admin.ProfileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.database_project_admin.Activities.admin_main_dashboard;
import com.example.database_project_admin.R;
import com.google.android.material.textfield.TextInputEditText;


public class setting_activity extends AppCompatActivity {
    private RelativeLayout rellay1,rally2,rellay2;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            rellay1.setVisibility(View.VISIBLE);
            rally2.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);

        }
    };
    private ProgressBar progressBar;
    private Handler progressBarh=new Handler();
    private Runnable runnable1=new Runnable()
    {
        @Override
        public void run()
        {

            progressBar.setVisibility(View.GONE);
        }
    };
    TextInputEditText username_tf_setting,current_password_tf_setting,new_password_tf_setting,confirm_password_tf_setting;
    RadioGroup Radio_Group_setting_font,Radio_Group_setting_text_size;
    int select_font,select_text_size;
    RadioButton font_radioButton,textSize_radioButton;
    Button back_button_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activity);

        rellay1 = findViewById(R.id.rellay_setting);
        rally2=findViewById(R.id.bottom_rally_setting);
        rellay2=findViewById(R.id.rellay2_setting);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        progressBar=findViewById(R.id.my_progress_bar);
        progressBarh.postDelayed(runnable1,100);

        //connecting TextInputEditText
        username_tf_setting=findViewById(R.id.username_tf_setting);
        current_password_tf_setting=findViewById(R.id.current_password_tf_setting);
        new_password_tf_setting=findViewById(R.id.new_password_tf_setting);
        confirm_password_tf_setting=findViewById(R.id.confirm_password_tf_setting);
        //connecting radio group
        Radio_Group_setting_font=findViewById(R.id.Radio_Group_setting_font);
        Radio_Group_setting_text_size=findViewById(R.id.Radio_Group_setting_text_size);
        //conecting button
        back_button_setting=findViewById(R.id.back_button_setting);
        back_button_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setting_activity.this, admin_main_dashboard.class));
            }
        });

        Radio_Group_setting_font.clearCheck();
        Radio_Group_setting_text_size.clearCheck();
        Radio_Group_setting_font.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                font_radioButton = Radio_Group_setting_font.findViewById(checkedId);
            }
        });
        Radio_Group_setting_text_size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                textSize_radioButton = Radio_Group_setting_text_size.findViewById(checkedId);
            }
        });
    }
}