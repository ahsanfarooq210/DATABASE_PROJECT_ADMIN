package com.example.database_project_admin.Target.Fragments.show;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database_project_admin.Entity.Sku;
import com.example.database_project_admin.R;

import com.example.database_project_admin.Target.Entity.Target;
import com.example.database_project_admin.Target.Entity.Target_SalesMen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class SKU_Target_Fragment extends Fragment {
    // to show progress bar things
    private ProgressBar circularProgressbar_overAll,circularProgressbar_specific;
    private TextView textView_overAll,textview_specific;
    private Button show_target_sku_button;

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
    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {

            rellay1.setVisibility(View.VISIBLE);
            rally3.setVisibility(View.VISIBLE);

        }
    };

    //handler for the progress bar
    private ProgressBar progressBar;
    private Handler progressBars = new Handler();
    private Runnable runnable1 = new Runnable()
    {
        @Override
        public void run()
        {
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    //array lists for the array adapters
    private List<Sku> skuList;
    List<Target> targetlist;
    //to get the email id of the salesman
    private FirebaseAuth auth;
    private FirebaseUser user;

    View view;
    View v;
    Sku skuSelected;
    public SKU_Target_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v=new View(getContext());
//show_progress_bar_LinearLayout
        rellay1 = v.findViewById(R.id.target_show_sku_layout);
        rally3 = v.findViewById(R.id.target_sku_bottom_rally2);

        //splash screen
        handler.postDelayed(runnable, 500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_s_k_u__target_, container, false);


        //initializing lists
        skuList = new ArrayList<>();
        targetlist=new ArrayList<>();
        //initializing database reference for downloading and uploading the data the data
        skuReference = FirebaseDatabase.getInstance().getReference("SKU");
        targetReference= FirebaseDatabase.getInstance().getReference("TARGET");
        //synchronizing the database for the offline use
        skuReference.keepSynced(true);
        targetReference.keepSynced(true);
        //progess bar
        circularProgressbar_overAll=view.findViewById(R.id.circularProgressbar_overAll_sku);
        textView_overAll=view.findViewById(R.id.textView_overAll_sku);


        rellay1 = view.findViewById(R.id.target_show_sku_layout);
        rally3 = view.findViewById(R.id.target_sku_bottom_rally2);
        rellay2=view.findViewById(R.id.show_progress_bar_LinearLayout);

        //initializing the spinners
        skuSpinner =  view.findViewById(R.id.show_target_sku_spinner);
        //setting the array adapters
        skuArrayAdapter = new ArrayAdapter(getContext(),  R.layout.spinner_text, skuList);
        skuArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        //show Target button initialization and on click listener
        show_target_sku_button =  view.findViewById(R.id.show_target_sku_button);
        show_target_sku_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 skuSelected = (Sku) skuSpinner.getSelectedItem();
                Query q =FirebaseDatabase.getInstance().getReference("TARGET").orderByChild("SKU_ID").equalTo(skuSelected.getId());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        targetlist.clear();
                        for(DataSnapshot targets:dataSnapshot.getChildren()  )
                        {
                            targetlist.add(targets.getValue(Target.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                final List<Target_SalesMen> target_salesMenList;
                target_salesMenList=new ArrayList<>();
                Query q1 =FirebaseDatabase.getInstance().getReference("Target_SalesMen").orderByChild("SKU_ID").equalTo(skuSelected.getId());
                q1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        target_salesMenList.clear();
                        for(DataSnapshot targets:dataSnapshot.getChildren()  )
                        {
                            target_salesMenList.add(targets.getValue(Target_SalesMen.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                int progres = 0;
                for (int i=0; i<target_salesMenList.size(); i++) {
                    progres += target_salesMenList.get(i).getAchieved();
                }
                int total=0;
                for (int i=0; i<targetlist.size(); i++) {
                    total += targetlist.get(i).getTARGET();
                }
                int  actualPercentage=(progres/total)*100;
                circularProgressbar_overAll.setProgress(actualPercentage);
                textView_overAll.setText(actualPercentage+"%");
                progressBars.postDelayed(runnable1,500);
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