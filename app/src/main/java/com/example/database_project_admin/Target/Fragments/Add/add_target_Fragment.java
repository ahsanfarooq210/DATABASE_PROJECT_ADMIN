package com.example.database_project_admin.Target.Fragments.Add;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.database_project_admin.Entity.Sku;
import com.example.database_project_admin.R;
import com.example.database_project_admin.Target.Entity.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class add_target_Fragment extends Fragment {


//variables

    private EditText add_target_et;
    private Button add_target_button;
    //database reference
    private DatabaseReference skuReference,targetReference;
    //splash screen relative layout
    private RelativeLayout rellay1, rally3, rellay2;

    //array adapters for the dropdown lists
    private ArrayAdapter<Sku> skuArrayAdapter;
    //dropdowns spinners
    private Spinner skuSpinner;


    //handler for the splash screen
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
            rally3.setVisibility(View.VISIBLE);

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
    //array lists for the array adapters
    private List<Sku> skuList;
    //to get the email id of the salesman
    private FirebaseAuth auth;
    private FirebaseUser user;
    View view;
    View v;
    public add_target_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         v=new View(getContext());

        rellay1 = v.findViewById(R.id.target_add_layout);
        rally3 = v.findViewById(R.id.adding_target_bottom);

        //splash screen
        handler.postDelayed(runnable, 500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_target_, container, false);

        //initializing lists
        skuList = new ArrayList<>();
        //initializing database reference for downloading and uploading the data the data
        skuReference = FirebaseDatabase.getInstance().getReference("SKU");
        targetReference= FirebaseDatabase.getInstance().getReference("TARGET");
        //synchronizing the database for the offline use
        skuReference.keepSynced(true);
        targetReference.keepSynced(true);

        //relative layouts
        rellay1 = view.findViewById(R.id.target_add_layout);
        rellay2=view.findViewById(R.id.adding_sku_rellay2);
        rally3 = view.findViewById(R.id.adding_target_bottom);



        //progress bar
        progressBar = view.findViewById(R.id.add_fragment_my_progress_bar);
        progressBarh.postDelayed(runnable1, 0);

        //initializing the spinners
        skuSpinner =  view.findViewById(R.id.target_add_Sku_spinner);
        //setting the array adapters
        skuArrayAdapter = new ArrayAdapter(getContext(),  R.layout.spinner_text, skuList);
        skuArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        add_target_et= view.findViewById(R.id.add_target_et);
        //show Target button initialization and on click listener
        add_target_button =  view.findViewById(R.id.add_target_button);
        add_target_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sku sku = (Sku) skuSpinner.getSelectedItem();
                if(add_target_et.getText().length()==0)
                {
                    add_target_et.setError("Please Enter suitable Target value for Selected Sku ");
                    progressBarh.postDelayed(runnable1,500);
                    return;
                }
                int  add_target=Integer.parseInt(add_target_et.getText().toString().trim());
                if(add_target==0)
                {
                    add_target_et.setError("Please Enter suitable Target value for Selected Sku ");
                    progressBarh.postDelayed(runnable1,500);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                String id=targetReference.push().getKey();
                Target target=new Target(id,sku,sku.getId(),add_target,new Date().getTime());
                if(id!=null)
                {
                   targetReference.child(id).setValue(target);
                }
                else
                {
                    Toast.makeText(getContext(),
                            "Error \n string id=null \n contact developer immediately",
                            Toast.LENGTH_SHORT).show();
                }

                progressBarh.postDelayed(runnable1,500);
            }
        });
return  view;
    }



    @Override
    public void onStart() {
        super.onStart();

           skuReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                skuList.clear();
                for (DataSnapshot sku : dataSnapshot.getChildren()) {
                    skuList.add(sku.getValue(Sku.class));
                }
                skuSpinner.setAdapter(skuArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error in download ing the data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}