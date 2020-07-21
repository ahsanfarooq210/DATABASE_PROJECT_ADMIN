package com.example.database_project_admin.ProfileActivities.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.database_project_admin.Activities.MainActivity;
import com.example.database_project_admin.Activities.admin_main_dashboard;
import com.example.database_project_admin.ProfileActivities.Entity.ProfileData;
import com.example.database_project_admin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class activity_Edit_Profile extends AppCompatActivity {
    EditText name_tf_edit_profile,cnic_tf_edit_profile,email_tf_edit_profile,dob_tf_edit_profile,contact_number_tf_edit_profile,education_tf_edit_profile; //splash screen relative layout
    //employeee name
    private FirebaseUser user;
    private FirebaseAuth auth;
    Button cancel_button,save_Profile_button;
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
    //profileData

    String adminEmail;
    boolean isprofileDatacomplete;
    //database reference
    private DatabaseReference profileDataReference;
    //array lists for the array adapters
    private List<ProfileData> profileDataList;
    SharedPreferences prefreences ;
    ScrollView scrollView_edit_Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit__profile);
        Intent recIntent=getIntent();
        prefreences = getSharedPreferences(getResources().getString(R.string.SharedPreferences_FileName),MODE_PRIVATE);
        adminEmail=prefreences.getString(getResources().getString(R.string.SharedPreferences_Admin),"");
        isprofileDatacomplete=prefreences.getBoolean(getResources().getString(R.string.SharedPreferences_isProfileDataComplete),false);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        //initializing databasee reference for downloading and uploading the data the data
        profileDataReference = FirebaseDatabase.getInstance().getReference("AdminProfileData");
        profileDataReference.keepSynced(true);
        profileDataList=new ArrayList<>();
        scrollView_edit_Profile=findViewById(R.id.scrollView_edit_Profile);

        name_tf_edit_profile=findViewById(R.id.name_tf_edit_profile);
        cnic_tf_edit_profile=findViewById(R.id.cnic_tf_edit_profile);
        email_tf_edit_profile=findViewById(R.id.email_tf_edit_profile);
        dob_tf_edit_profile=findViewById(R.id.dob_tf_edit_profile);
        contact_number_tf_edit_profile=findViewById(R.id.contact_number_tf_edit_profile);
        education_tf_edit_profile=findViewById(R.id.education_tf_edit_profile);

        email_tf_edit_profile.setText(adminEmail);
        email_tf_edit_profile.setEnabled(false);

        cancel_button=findViewById(R.id.cancel_button);
        save_Profile_button=findViewById(R.id.save_Profile_button);
        //relative layouts
        rellay1 = findViewById(R.id.adding_sku_rellay1);
        rellay2 = findViewById(R.id.adding_sku_rellay2);
        rally3 = findViewById(R.id.adding_sku_bottom_rally2);

        //splash screen
        handler.postDelayed(runnable, 500);

        //progress bar
        progressBar = findViewById(R.id.my_progress_bar);
        progressBarh.postDelayed(runnable1, 0);

        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            if(imm.isActive())
            {
                scrollView_edit_Profile.setOnTouchListener( new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        return false;
                    }
                });

            }
            if(!imm.isActive())
            {
                scrollView_edit_Profile.setOnTouchListener( new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        return true;
                    }
                });
            }
        }
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isprofileDatacomplete)
                {
                    Intent send =new Intent(activity_Edit_Profile.this, admin_main_dashboard.class);
                    startActivity(send);
                }
                if(!isprofileDatacomplete)
                {
                    //rwccf
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(activity_Edit_Profile.this).inflate(R.layout.dialog_for_delete_confirmation, viewGroup, false);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity_Edit_Profile.this);
                    builder.setView(dialogView);
                    TextView text= dialogView.findViewById(R.id.text_dialog_confirmation);
                    text.setText("Are you sure to Logout");
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button dialog_yes_Button = (Button)dialogView.findViewById(R.id.yes_button);
                    dialog_yes_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            progressBar.setVisibility(View.VISIBLE);
                            Handler logoutHandler=new Handler();
                            rellay2.setVisibility(View.VISIBLE);
                            rellay1.setVisibility(View.GONE);
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


            }
        });
        save_Profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isprofileDatacomplete) {

                    String name, CNIC, Date_of_birth, cell_number, Education;
                    name = name_tf_edit_profile.getText().toString();
                    CNIC = cnic_tf_edit_profile.getText().toString().trim();
                    Date_of_birth = dob_tf_edit_profile.getText().toString().trim();
                    cell_number = contact_number_tf_edit_profile.getText().toString().trim();
                    Education = education_tf_edit_profile.getText().toString().trim();
                    if (name.length() == 0 || !validateFields(name)) {
                        name_tf_edit_profile.setError("Please Enter Suitable Name ");
                        return;
                    }

                    if (CNIC.length() == 0 || CNIC.length() < 13) {
                        cnic_tf_edit_profile.setError("Please Enter Suitable CNIC ");
                        return;
                    }
                    if (Date_of_birth.length() == 0) {
                        dob_tf_edit_profile.setError("Please Enter Suitable Date OF Birth ");
                        return;
                    }
                    if (cell_number.length() != 11) {
                        contact_number_tf_edit_profile.setError("Please Enter Suitable Contact Number ");
                        return;
                    }
                    if (Education.length() == 0) {
                        education_tf_edit_profile.setError("Please Enter Suitable Education ");
                        return;
                    }

                    String id = profileDataReference.push().getKey();

                    ProfileData profileData = new ProfileData(id, name, CNIC, adminEmail, Date_of_birth, cell_number, Education);

                    if (id != null) {
                        profileDataReference.child(id).setValue(profileData);
                    } else {
                        Toast.makeText(activity_Edit_Profile.this, "Error \n string id=null \n contact developer immediately", Toast.LENGTH_SHORT).show();

                    }

                    Intent send =new Intent(activity_Edit_Profile.this, admin_main_dashboard.class);
                    SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.SharedPreferences_FileName),MODE_PRIVATE).edit();
                    editor.putBoolean(getResources().getString(R.string.SharedPreferences_isProfileDataComplete),true);
                    editor.commit();
                    startActivity(send);

                }

                if(isprofileDatacomplete)
                {

                    String name, CNIC, Date_of_birth, cell_number, Education;
                    name = name_tf_edit_profile.getText().toString();
                    CNIC = cnic_tf_edit_profile.getText().toString().trim();
                    Date_of_birth = dob_tf_edit_profile.getText().toString().trim();
                    cell_number = contact_number_tf_edit_profile.getText().toString().trim();
                    Education = education_tf_edit_profile.getText().toString().trim();
                    if (name.length() == 0 || !validateFields(name)) {
                        name_tf_edit_profile.setError("Please Enter Suitable Name ");
                        return;
                    }

                    if (CNIC.length() == 0 || CNIC.length() < 13) {
                        cnic_tf_edit_profile.setError("Please Enter Suitable CNIC ");
                        return;
                    }
                    if (Date_of_birth.length() == 0) {
                        dob_tf_edit_profile.setError("Please Enter Suitable Date OF Birth ");
                        return;
                    }
                    if (cell_number.length() != 11) {
                        contact_number_tf_edit_profile.setError("Please Enter Suitable Contact Number ");
                        return;
                    }
                    if (Education.length() == 0) {
                        education_tf_edit_profile.setError("Please Enter Suitable Education ");
                        return;
                    }

                    String id = profileDataList.get(0).getProfileDataID();

                    if (id != null) {
                        profileDataReference.child(id).child("name").setValue(name);
                        profileDataReference.child(id).child("cnic").setValue(CNIC);
                        profileDataReference.child(id).child("date_of_birth").setValue(Date_of_birth);

                        profileDataReference.child(id).child("cell_number").setValue(cell_number);

                        profileDataReference.child(id).child("education").setValue(Education);

                    } else {
                        Toast.makeText(activity_Edit_Profile.this, "Error \n string id=null \n contact developer immediately", Toast.LENGTH_SHORT).show();

                    }

                    Intent send =new Intent(activity_Edit_Profile.this, admin_main_dashboard.class);
                    SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.SharedPreferences_FileName),MODE_PRIVATE).edit();
                    editor.putBoolean(getResources().getString(R.string.SharedPreferences_isProfileDataComplete),true);
                    editor.commit();
                    startActivity(send);

                }


            }
        });
    }





    Runnable logout=new Runnable()
    {
        @Override
        public void run()
        {
            auth.getInstance().signOut();

            progressBarh.postDelayed(runnable1,0);
            Intent intent=new Intent(activity_Edit_Profile.this, MainActivity.class);
            startActivity(intent);
        }
    };
    private boolean validateFields(String n) {
        String names[]= n.split("\\s+");
        if((names.length <2))
        {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isprofileDatacomplete) {
            super.onBackPressed();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        profileDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileDataList.clear();
                for(DataSnapshot profile: dataSnapshot.getChildren()) {
                    if (isprofileDatacomplete) {
                        if (profile.getValue(ProfileData.class).getEmail().equals(adminEmail))
                            profileDataList.add(profile.getValue(ProfileData.class));
                    }

                }

                if(isprofileDatacomplete)
                {
                    name_tf_edit_profile.setText(profileDataList.get(0).getName());
                    cnic_tf_edit_profile.setText(profileDataList.get(0).getCNIC());
                    dob_tf_edit_profile.setText(profileDataList.get(0).getDate_of_birth());
                    contact_number_tf_edit_profile.setText(profileDataList.get(0).getCell_number());
                    education_tf_edit_profile.setText(profileDataList.get(0).getEducation());

                }
                if(!isprofileDatacomplete)
                {
                    cancel_button.setText("Log Out");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}