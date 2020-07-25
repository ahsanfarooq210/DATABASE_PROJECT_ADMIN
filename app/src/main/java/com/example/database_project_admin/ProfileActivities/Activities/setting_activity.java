package com.example.database_project_admin.ProfileActivities.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.database_project_admin.Activities.MainActivity;
import com.example.database_project_admin.Activities.admin_main_dashboard;
import com.example.database_project_admin.ProfileActivities.Entity.ProfileData;
import com.example.database_project_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


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
    Button back_button_setting,cancel,save;
    SharedPreferences prefreences ;
    String adminEmail;
    //database reference
    private DatabaseReference profileDataReference;
    //array lists for the array adapters
    private List<ProfileData> profileDataList;
    FirebaseUser user;
     FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activity);
        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        prefreences = getSharedPreferences(getResources().getString(R.string.SharedPreferences_FileName),MODE_PRIVATE);
        adminEmail=prefreences.getString(getResources().getString(R.string.SharedPreferences_AdminEmail),"");

        rellay1 = findViewById(R.id.rellay_setting);
        rally2=findViewById(R.id.bottom_rally_setting);
        rellay2=findViewById(R.id.rellay2_setting);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        progressBar=findViewById(R.id.my_progress_bar);
        progressBarh.postDelayed(runnable1,100);
        //initializing databasee reference for downloading and uploading the data the data
        profileDataReference = FirebaseDatabase.getInstance().getReference("AdminProfileData");
        profileDataReference.keepSynced(true);
        profileDataList=new ArrayList<>();
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
        cancel=findViewById(R.id.cancel_password_button);
        save=findViewById(R.id.change_password_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username_tf_setting.getText().toString().trim().equals(""))
                {
                    cancelOnclick();
                }
                if(!current_password_tf_setting.getText().toString().trim().equals(""))
                {
                    cancelOnclick();
                }
                if(!new_password_tf_setting.getText().toString().trim().equals(""))
                {
                    cancelOnclick();
                }
                if(!confirm_password_tf_setting.getText().toString().trim().equals(""))
                {
                    cancelOnclick();
                }
                if(username_tf_setting.getText().toString().trim().equals("") &&
                   current_password_tf_setting.getText().toString().trim().equals("") &&
                        new_password_tf_setting.getText().toString().trim().equals("") &&
                        confirm_password_tf_setting.getText().toString().trim().equals("")
                )
                {
                    Intent intent=new Intent(setting_activity.this,admin_main_dashboard.class);
                    startActivity(intent);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username_tf_setting.getText().toString().trim().equals(""))
                {
                    username_tf_setting.setError("Please enter Email");
                    return;
                }
                if(!username_tf_setting.getText().toString().trim().equals(prefreences.getString(getString(R.string.SharedPreferences_AdminEmail),"")))
                {
                    username_tf_setting.setError("Please enter yourCorrect Email ");

                    return;

                }
                if(current_password_tf_setting.getText().toString().trim().equals(""))
                {
                   current_password_tf_setting.setError("Please Enter your Current password");
                return;
                }
                String pass=profileDataList.get(0).getPassword();
                String password= current_password_tf_setting.getText().toString();
                if(!password.equals(pass))
                {
                    current_password_tf_setting.setError("Current Password is not correct");
                    return;
                }
                if(new_password_tf_setting.getText().toString().trim().equals(""))
                {
                    new_password_tf_setting.setError("Please Enter New Password");
                return;
                }
                if(confirm_password_tf_setting.getText().toString().trim().equals(""))
                {
                    confirm_password_tf_setting.setError("Please Enter Password Again");
                return;
                }
                    changePassword();

            }
        });
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

    private void changePassword() {
        String userEmail=username_tf_setting.getText().toString().trim();

        final String email = user.getEmail();
        if(!userEmail.equals(email))
        {
            username_tf_setting.setError("Please enter your Correct Email ");

            return;
        }

        final String password=profileDataList.get(0).getPassword();
        String oldpass=current_password_tf_setting.getText().toString().trim();
        if(!oldpass.equals(password))
        {
            current_password_tf_setting.setError("Current is not correct");
            current_password_tf_setting.setTextColor(Color.RED);

            return;
        }
        //if()
        final String newpasswpod=new_password_tf_setting.getText().toString().trim();
        String confirm=confirm_password_tf_setting.getText().toString().trim();
        if(!newpasswpod.equals(confirm))
        {
            new_password_tf_setting.setError("new Password and confirm does Not matched");
            confirm_password_tf_setting.setError("new Password and confirm does Not matched");
            return;
        }
        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newpasswpod).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(setting_activity.this,"Something went wrong. Please try again later",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(setting_activity.this,"Password Successfully Modified",Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.VISIBLE);

                                String profileId=profileDataList.get(0).getProfileDataID();
                                profileDataReference.child(profileId).child("password").setValue(newpasswpod);
                                // activity_Edit_Profile
                                SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.SharedPreferences_FileName),MODE_PRIVATE).edit();
                                editor.putString(getResources().getString(R.string.SharedPreferences_AdminEmail),"");
                                editor.putString(getString(R.string.SharedPreferences_AdminPassword),"");
                                editor.putBoolean(getResources().getString(R.string.SharedPreferences_isProfileDataComplete),false);
                                editor.commit();
                                Handler logoutHandler=new Handler();
                                logoutHandler.postDelayed(logout,2000);
                                    auth.getInstance().signOut();


                            }
                        }
                    });
                }else {
                    Toast.makeText(setting_activity.this,"Authentication Failed",Toast.LENGTH_LONG).show();

                }
            }
        });
    }



    public void cancelOnclick(){
        //rwccf
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(setting_activity.this).inflate(R.layout.dialog_for_delete_confirmation, viewGroup, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(setting_activity.this);
        builder.setView(dialogView);
        TextView text= dialogView.findViewById(R.id.text_dialog_confirmation);
        text.setText("Are you sure to cancel");
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialog_yes_Button = (Button)dialogView.findViewById(R.id.yes_button);
        dialog_yes_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                progressBar.setVisibility(View.VISIBLE);
                Handler logoutHandler=new Handler();
                logoutHandler.postDelayed(logout,2000);

            }
        }); Button dialog_no_Button = (Button)dialogView.findViewById(R.id.No_button);
        dialog_no_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //Fragment fragment=new View_Today_Report_Fragment();


            }
        });
        Button dialog_cancel_Button = (Button)dialogView.findViewById(R.id.cancel_button);
        dialog_cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }
    Runnable logout=new Runnable()
    {
        @Override
        public void run()
        {
            progressBarh.postDelayed(runnable1,0);
            Intent intent=new Intent(setting_activity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        profileDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileDataList.clear();
                for(DataSnapshot profile: dataSnapshot.getChildren()) {
                        if (profile.getValue(ProfileData.class).getEmail().equals(adminEmail))
                            profileDataList.add(profile.getValue(ProfileData.class));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}