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

import com.example.database_project_admin.R;
import com.example.database_project_admin.SalesmanId;
import com.example.database_project_admin.Sku;
import com.example.database_project_admin.Target.Target;
import com.example.database_project_admin.Target.Target_SalesMen;
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
public class Target_SalesMen_Fragment extends Fragment {

    // to show progress bar things
    private ProgressBar circularProgressbar;
    TextView textView;

    private Button show_target_salesmen_button;

    //database reference
    private DatabaseReference targetReference;
    private DatabaseReference salesmanReference;
    private List<SalesmanId> salesmanIdList;
    //splash screen relative layout
    private RelativeLayout rellay1, rally3, rellay2;

    //array adapters for the dropdown lists
    private ArrayAdapter<Sku> skuArrayAdapter;
    //dropdowns spinners
    private Spinner salesSpinner;


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
    private ArrayAdapter <SalesmanId>salesmanAdapter;
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
    //to get the email id of the salesman
    private FirebaseAuth auth;
    private FirebaseUser user;

    View view;
    View v;

    public Target_SalesMen_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v=new View(getContext());
//show_progress_bar_LinearLayout
        rellay1 = v.findViewById(R.id.target_show_salesmen_layout);
        rally3 = v.findViewById(R.id.target_salesmen_bottom_rally2);

        //splash screen
        handler.postDelayed(runnable, 500);
    }
List<Target_SalesMen>target_salesMenList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_target__sales_men_, container, false);


        //initializing lists
        skuList = new ArrayList<>();
        //initializing database reference for downloading and uploading the data the data
        salesmanReference= FirebaseDatabase.getInstance().getReference("SALESMAN_USERNAME");
        targetReference= FirebaseDatabase.getInstance().getReference("TARGET");
        //synchronizing the database for the offline use
        salesmanIdList=new ArrayList<>();

        salesmanReference.keepSynced(true);

        salesmanAdapter=new ArrayAdapter(getContext(),R.layout.spinner_text,salesmanIdList);
        salesmanAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        targetReference.keepSynced(true);
        //progess bar
        circularProgressbar=view.findViewById(R.id.circularProgressbar_overAll_sku);
        textView=view.findViewById(R.id.textView_overAll_sku);
        rellay1 = view.findViewById(R.id.target_show_salesmen_layout);
        rally3 = view.findViewById(R.id.target_salesmen_bottom_rally2);
        rellay2=view.findViewById(R.id.show_salemen_progress_bar_LinearLayout);

        //initializing the spinners
        salesSpinner =  view.findViewById(R.id.show_target_salesmen_spinner);
        //setting the array adapters
        skuArrayAdapter = new ArrayAdapter(getContext(),  R.layout.spinner_text, skuList);
        skuArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        //show Target button initialization and on click listener
        show_target_salesmen_button =  view.findViewById(R.id.show_target_salesmen_button);
        show_target_salesmen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                target_salesMenList=new ArrayList<>();
                SalesmanId salesmanId= (SalesmanId) salesSpinner.getSelectedItem();
                final List<Target> targetList=new ArrayList<>();
                Query q =FirebaseDatabase.getInstance().getReference("TARGET").orderByChild("SKU_ID").equalTo(salesmanId.getId());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        targetList.clear();
                        for(DataSnapshot targets:dataSnapshot.getChildren()  )
                        {
                            targetList.add(targets.getValue(Target.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                final List<Target_SalesMen> target_salesMenList = null;
                Query q1 =FirebaseDatabase.getInstance().getReference("Target_SalesMen").orderByChild("SaleMen_ID").equalTo(salesmanId.getId());
                q1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        target_salesMenList.clear();
                        for(DataSnapshot target_salesMen_dataShot:dataSnapshot.getChildren())
                        {
                            target_salesMenList.add(target_salesMen_dataShot.getValue(Target_SalesMen.class));
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
                for (int i=0; i<targetList.size(); i++) {
                    total += targetList.get(i).getTARGET();
                }
                int  actualPercentage=(progres/total)*100;
                circularProgressbar.setProgress(actualPercentage);
                textView.setText(actualPercentage+"%");
                progressBars.postDelayed(runnable1,500);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        salesmanReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                skuList.clear();
                for (DataSnapshot sku : dataSnapshot.getChildren()) {
                    skuList.add(sku.getValue(Sku.class));
                }
                salesSpinner.setAdapter(skuArrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error in download ing the data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}