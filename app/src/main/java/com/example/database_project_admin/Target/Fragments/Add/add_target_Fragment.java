package com.example.database_project_admin.Target.Fragments.Add;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database_project_admin.Entity.SalesmanId;
import com.example.database_project_admin.Entity.Sku;
import com.example.database_project_admin.R;
import com.example.database_project_admin.Target.Entity.Target;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class add_target_Fragment extends Fragment {


//variables
String startDateString ,endDateString;
    private Date startDate,endDate;

    private MaterialDatePicker.Builder DateBuilder;
    private MaterialDatePicker DateMaterialDatePicker;

    private EditText add_target_et;
    private Button add_target_button,select_date_button;
    //database reference
    private DatabaseReference skuReference,targetReference, salesMenRefernce;
    //splash screen relative layout
    private RelativeLayout rellay1, rally3, rellay2;

    //array adapters for the dropdown lists
    private ArrayAdapter<Sku> skuArrayAdapter;
    private  ArrayAdapter<SalesmanId> salesmanIdArrayAdapter;
    //dropdowns spinners
    private Spinner skuSpinner,salesmenSpinner;
    //array lists for the array adapters
    private List<Sku> skuList;
    private List<SalesmanId> salesmanIdList;
    private List<Target> targetList;

    boolean spinner_sku = false, spinner_salesmen=false, range_button=false;
    AdapterView.OnItemSelectedListener skuSpinnerSelectedListener,salesMenSpinnerSelectedListener ;
    TextView target_add_Sku_spinner_Error,target_add_SalesMen_spinner_Error,select_date_button_Error;
    View sku_view ;
    View salesmen_view;
    View RangeButtonView;
    TextView tvListItem_sku ;
    TextView tvListItem_salesmen ;
    TextView textView_button;
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
        salesmanIdList=new ArrayList<>();
        targetList=new ArrayList<>();
        //initializing database reference for downloading and uploading the data the data
        skuReference = FirebaseDatabase.getInstance().getReference("SKU");
        targetReference= FirebaseDatabase.getInstance().getReference("TARGET");
        salesMenRefernce=FirebaseDatabase.getInstance().getReference("SALESMAN_USERNAME");
        //synchronizing the database for the offline use
        skuReference.keepSynced(true);
        targetReference.keepSynced(true);
        salesMenRefernce.keepSynced(true);

        //relative layouts
        rellay1 = view.findViewById(R.id.target_add_layout);
        rellay2=view.findViewById(R.id.adding_sku_rellay2);
        rally3 = view.findViewById(R.id.adding_target_bottom);



        //progress bar
        progressBar = view.findViewById(R.id.add_fragment_my_progress_bar);
        progressBarh.postDelayed(runnable1, 0);

        //initializing the spinners
        skuSpinner =  view.findViewById(R.id.target_add_Sku_spinner);
        salesmenSpinner=view.findViewById(R.id.target_add_SalesMen_spinner);
        //setting the array adapters
        skuArrayAdapter = new ArrayAdapter(getContext(),  R.layout.spinner_text, skuList);
        skuArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        salesmanIdArrayAdapter=new ArrayAdapter<>(getContext(),R.layout.spinner_text,salesmanIdList);
        salesmanIdArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        target_add_SalesMen_spinner_Error=view.findViewById(R.id.target_add_SalesMen_spinner_Error);

        target_add_Sku_spinner_Error=view.findViewById(R.id.target_add_Sku_spinner_Error);
        select_date_button_Error=view.findViewById(R.id.select_date_button_Error);

        add_target_et= view.findViewById(R.id.add_target_et);
        //show Target button initialization and on click listener

        //MaterialDatePicker
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+05:00"));
        calendar.clear();

        final long today = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(today);


        DateBuilder=MaterialDatePicker.Builder.dateRangePicker();
        DateBuilder.setTitleText("Select Date Range ");
        DateMaterialDatePicker=MaterialDatePicker.Builder.dateRangePicker().setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar).build();

        DateMaterialDatePicker.clearOnPositiveButtonClickListeners();
        DateMaterialDatePicker.clearOnCancelListeners();


        DateMaterialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                String end_date=selection.toString();
                String[] result = end_date.split("\\s");
                //getting start date
                String start=result[0].substring(5,result[0].length());
                startDate=new Date(Long.parseLong(start));
                startDateString=dateToString(startDate);
                // getting End date
                String end=result[1].substring(0,result[1].length()-1);
                endDate=new Date(Long.parseLong(end));
                endDateString=dateToString(endDate);
                range_button=true;

                // Toast.makeText(getContext(),endDateString,Toast.LENGTH_LONG).show();

            }
        });
        DateMaterialDatePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                DateMaterialDatePicker.clearOnCancelListeners();
                DateMaterialDatePicker.clearOnPositiveButtonClickListeners();
                DateMaterialDatePicker.clearOnDismissListeners();
               // DateMaterialDatePicker.dismiss();
                DateMaterialDatePicker.getDialog().cancel();
            }
        });
        select_date_button=view.findViewById(R.id.select_date_button);

        select_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateMaterialDatePicker.show(getActivity().getSupportFragmentManager(),"Select End Date");
                select_date_button_Error.setVisibility(View.GONE);
                select_date_button.setError(null);
            }
        });

spinner_salesmen=false;
spinner_sku=false;
skuSpinner.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        spinner_sku = true;
        return false;
    }
});
salesmenSpinner.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        spinner_salesmen = true;
        return false;
    }
});
      skuSpinnerSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                if (spinner_sku)
                {
                    target_add_Sku_spinner_Error.setVisibility(View.GONE);
                }
                else if (!spinner_sku)
                {
                    SetError("Please Select Sales SKU First ","SKU");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                spinner_sku=false;
            }
        };
        salesMenSpinnerSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container, int position, long id) {
                if (spinner_salesmen)
                {
                    target_add_SalesMen_spinner_Error.setVisibility(View.GONE);
                }
                else if (!spinner_salesmen)
                {
                    SetError("Please Select Sales Men First ","SALESMEN");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                spinner_salesmen=false;
            }
        };


        add_target_button =  view.findViewById(R.id.add_target_button);
        add_target_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skuSpinner.setOnItemSelectedListener(skuSpinnerSelectedListener);
                salesmenSpinner.setOnItemSelectedListener(salesMenSpinnerSelectedListener);
                if(!spinner_sku)
                {
                    SetError("Please Select Sales SKU First ","SKU");

                }
                if( !spinner_salesmen)
                {
                    SetError("Please Select Sales Men First ","SALESMEN");
                }
                if(spinner_sku&& spinner_salesmen) {

                    Sku sku = (Sku) skuSpinner.getSelectedItem();
                    SalesmanId salesmanId = (SalesmanId) salesmenSpinner.getSelectedItem();
                    if (add_target_et.getText().length() == 0) {
                        add_target_et.setError("Please Enter suitable Target value for Selected Sku ");
                        progressBarh.postDelayed(runnable1, 500);
                        return;
                    }
                    int add_target = Integer.parseInt(add_target_et.getText().toString().trim());
                    if (add_target == 0) {
                        add_target_et.setError("Please Enter suitable Target value for Selected Sku ");
                        progressBarh.postDelayed(runnable1, 500);
                        return;
                    }

                    if(!range_button)
                    {
                        select_date_button.setError("Please Select Range For Target First ");
                        SetError("Please Select Range For Target First ", "RANGE_BUTTON");
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    String targetID = targetReference.push().getKey();
                    String skuID=sku.getId();
                    String status="Active";
                    Target target = new Target(targetID, sku, skuID, salesmanId.getEmail(), add_target, startDateString, endDateString,status);
                    if (targetID != null) {
                        targetReference.child(targetID).setValue(target);

                    } else {
                        Toast.makeText(getContext(),
                                "Error \n string id=null \n contact developer immediately",
                                Toast.LENGTH_SHORT).show();
                    }

                    add_target_et.setText("");
                    skuSpinner.setSelection(0);
                    salesmenSpinner.setSelection(0);
                    spinner_sku=false;
                    spinner_salesmen=false;
                    progressBarh.postDelayed(runnable1, 500);
                  Toast.makeText(getContext(),"Target Set Successfully ",Toast.LENGTH_LONG).show();

                }
            }
        });
return  view;
    }
    public void SetError(String errorMessage,String FOR)
    {
        skuSpinner.getSelectedView();
        salesmenSpinner.getSelectedView();
        tvListItem_sku = (TextView)sku_view;
        tvListItem_salesmen = (TextView)salesmen_view;
        textView_button=(TextView)RangeButtonView;
        if(errorMessage != null)
        {   //
            if(FOR.equals("SKU")) {

                target_add_Sku_spinner_Error.requestFocus();
                target_add_Sku_spinner_Error.setError(errorMessage);
            }
            else if(FOR.equals("SALESMEN"))
            {

                target_add_SalesMen_spinner_Error.requestFocus();
                target_add_SalesMen_spinner_Error.setError(errorMessage);
            }
            else if (FOR.equals("RANGE_BUTTON"))
            {
                select_date_button_Error.requestFocus();
                select_date_button_Error.setError(errorMessage);
            }

        }
        else
        {
            tvListItem_sku.setError(null);
            tvListItem_salesmen.setError(null);
            target_add_Sku_spinner_Error.setError(null);
            target_add_SalesMen_spinner_Error.setError(null);
        }
    }
    private String dateToString(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    private Date convertStringToDate(String sDate1) throws ParseException
    {
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        return date1;
    }


    @Override
    public void onStart() {
        super.onStart();
targetReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        targetList.clear();
        for (DataSnapshot target : dataSnapshot.getChildren())
        {
            targetList.add(target.getValue(Target.class));
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
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
           salesMenRefernce.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   salesmanIdList.clear();
                   for (DataSnapshot salesmen : dataSnapshot.getChildren())
                   {
                       salesmanIdList.add(salesmen.getValue(SalesmanId.class));
                   }
                   salesmenSpinner.setAdapter(salesmanIdArrayAdapter);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
    }
}