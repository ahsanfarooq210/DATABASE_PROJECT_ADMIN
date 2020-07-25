package com.example.database_project_admin.Target.Fragments.show;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private DatabaseReference skuReference,targetReference, targetSalesmanreference;

    //splash screen relative layout
    private RelativeLayout rellay1, rally3, rellay2;

    //array adapters for the dropdown lists
    private ArrayAdapter<Sku> skuArrayAdapter;
    //dropdowns spinners
    private Spinner skuSpinner;
    AdapterView.OnItemSelectedListener skuSpinnerSelectedListener;
    TextView show_target_sku_spinner_Error;
boolean show_target_sku_spinner=false;
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
    List<Target> target_list_from_dataBase;
    List<Target> target_list_filter;

    List<Target_SalesMen> target_salesMan_list_from_dataBase;
    List<Target_SalesMen> target_salesMan_list_filter;

    //to get the email id of the salesman

    private FirebaseAuth auth;
    private FirebaseUser user;
    TextView id;
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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_s_k_u__target_, container, false);


        //initializing lists
        skuList = new ArrayList<>();
        target_list_from_dataBase=new ArrayList<>();
        target_list_filter=new ArrayList<>();
        target_salesMan_list_filter=new ArrayList<>();
        target_salesMan_list_from_dataBase=new ArrayList<>();
        //initializing database reference for downloading and uploading the data the data
        skuReference = FirebaseDatabase.getInstance().getReference("SKU");
        targetReference= FirebaseDatabase.getInstance().getReference("TARGET");
        targetSalesmanreference=FirebaseDatabase.getInstance().getReference("TargetSalesMan");
        //synchronizing the database for the offline use
        skuReference.keepSynced(true);
        targetSalesmanreference.keepSynced(true);
        targetReference.keepSynced(true);
        //progess bar
        circularProgressbar_overAll=view.findViewById(R.id.circularProgressbar_overAll_sku);
        textView_overAll=view.findViewById(R.id.textView_overAll_sku);
        id=view.findViewById(R.id.id);

        rellay1 = view.findViewById(R.id.target_show_sku_layout);
        rally3 = view.findViewById(R.id.target_sku_bottom_rally2);
        rellay2=view.findViewById(R.id.show_progress_bar_LinearLayout);

        //initializing the spinners
        skuSpinner =  view.findViewById(R.id.show_target_sku_spinner);
        //setting the array adapters
        skuArrayAdapter = new ArrayAdapter(getContext(),  R.layout.spinner_text, skuList);
        skuArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        show_target_sku_spinner=false;
        show_target_sku_spinner_Error=view.findViewById(R.id.show_target_sku_spinner_Error);
        skuSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                 show_target_sku_spinner= true;
                return false;
            }
        });
        skuSpinnerSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                show_target_sku_spinner=true;
                if (show_target_sku_spinner)
                {
                    show_target_sku_spinner_Error.setVisibility(View.GONE);
                }
                else if (!show_target_sku_spinner)
                {
                    SetError("Please Select Sales SKU First ");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                show_target_sku_spinner=false;
            }
        };

        //show Target button initialization and on click listener
        show_target_sku_button =  view.findViewById(R.id.show_target_sku_button);

        show_target_sku_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skuSpinner.setOnItemSelectedListener(skuSpinnerSelectedListener);
                if(!show_target_sku_spinner)
                {
                    SetError("Please Select Sales SKU First ");
                }
                if(show_target_sku_spinner)
                {
                skuSelected = (Sku) skuSpinner.getSelectedItem();
                String skuID= skuSelected.getId();
                for(int t=0; t<target_list_from_dataBase.size();t++)
                {
                    if(target_list_from_dataBase.get(t).getSkuID().equals(skuID))
                    {
                        target_list_filter.add(target_list_from_dataBase.get(t));
                    }
                }

               for (int ts=0; ts<target_salesMan_list_from_dataBase.size(); ts++)
               {
                   if(target_salesMan_list_from_dataBase.get(ts).getSKU_ID().equals(skuID))
                   {
                       target_salesMan_list_filter.add(target_salesMan_list_from_dataBase.get(ts));
                   }
               }
               if(target_list_filter.size()==0)
               {
                   Toast.makeText(getContext(),"please add Target first for selected SKU",Toast.LENGTH_LONG).show();
                   return;
               }
               if(target_salesMan_list_filter.size()==0)
               {
                   Toast.makeText(getContext(),"No orders is placed by any of SalesMen yet for selected SKU",Toast.LENGTH_LONG).show();
               return;
               }

               int progres = 0;
               for(int pts=0;pts<target_salesMan_list_filter.size();pts++)
               {
                   progres+=target_salesMan_list_filter.get(pts).getAchieved();
               }
               int total=0;
                for(int tt=0;tt<target_list_filter.size();tt++)
                {
                    total+=target_list_filter.get(tt).getTARGET();
                }
                    double  actualPercentage;
                  actualPercentage=(double) progres/(double) total;
                  actualPercentage=actualPercentage*100;
                  int actual=(int)Math.round(actualPercentage);
                circularProgressbar_overAll.setProgress(actual);
                textView_overAll.setText(actual+"%");
                progressBars.postDelayed(runnable1,500);
                return;
                }

            }
        });


        return  view;
    }
    public void SetError(String errorMessage)
    {
        skuSpinner.getSelectedView();

        if(errorMessage != null)
        {   //
            show_target_sku_spinner_Error.requestFocus();
            show_target_sku_spinner_Error.setError(errorMessage);

        }
        else
        {
            show_target_sku_spinner_Error.setError(null);
            show_target_sku_spinner_Error.setError(null);
        }
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
        targetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                target_list_from_dataBase.clear();
                for (DataSnapshot target : dataSnapshot.getChildren())
                {
                    if(target.getValue(Target.class).getTargetStatus().equals("Active"))
                    {
                        target_list_from_dataBase.add(target.getValue(Target.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        targetSalesmanreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                target_salesMan_list_from_dataBase.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(Target_SalesMen.class).getStatus().equals("Active")) {
                        target_salesMan_list_from_dataBase.add(snapshot.getValue(Target_SalesMen.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       /* Query q= FirebaseDatabase.getInstance().getReference().child("TargetSalesMan").orderByChild("status").equalTo("Active");
        q.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                target_salesMan_list_from_dataBase.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    target_salesMan_list_from_dataBase.add(snapshot.getValue(Target_SalesMen.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getContext(), "Error in downloading the data", Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}