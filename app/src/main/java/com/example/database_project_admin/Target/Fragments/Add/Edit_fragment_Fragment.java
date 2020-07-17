package com.example.database_project_admin.Target.Fragments.Add;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.database_project_admin.Entity.SalesmanId;
import com.example.database_project_admin.Entity.Sku;
import com.example.database_project_admin.R;

import com.example.database_project_admin.Target.Entity.Target;
import com.example.database_project_admin.Target.RecyclerViewContent.classes.RecyclerItemClickListener;
import com.example.database_project_admin.Target.RecyclerViewContent.Adapter.RecyclerViewAdapter;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
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
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Edit_fragment_Fragment extends Fragment {

    ImageView headerIndicator;
    ExpansionLayout expansionLayout;
    TextView salesman_edit_target,target_edit_target,start_date_edit_target,end_date_edit_target;
    //sku
    TextView company_sku_expandable,category_sku_expandable,product_name_sku_expandable, product_size_sku_expandable;

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
    private RelativeLayout rellay1, rally3, rellay2,main_view;

    //array adapters for the dropdown lists
    private ArrayAdapter<Sku> skuArrayAdapter;
    private  ArrayAdapter<SalesmanId> salesmanIdArrayAdapter;
    //dropdowns spinners
    private Spinner skuSpinner,salesmenSpinner;
    //array lists for the array adapters
    private List<Sku> skuList;
    private List<SalesmanId> salesmanIdList;
    private ArrayList<Target> targetList;

    //handler for the splash screen
    private  Handler recycleViewHandler =new Handler();
    private  Runnable recycleViewRunnabe =new Runnable() {
        @Override
        public void run() {
      main_view.setVisibility(View.VISIBLE);
        }
    };
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

    //recyclerView
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    public Edit_fragment_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v=new View(getContext());

        //  rellay1 = v.findViewById(R.id.target_edit_layout);
      //  rally3 = v.findViewById(R.id.adding_target_bottom_edit);
        main_view=v.findViewById(R.id.main_view);
        recycleViewHandler.postDelayed(recycleViewRunnabe,500);
        //splash screen
//        handler.postDelayed(runnable, 500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_edit_target, container, false);

        //initializing lists
        skuList = new ArrayList<>();
        salesmanIdList=new ArrayList<>();
        targetList=new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing database reference for downloading and uploading the data the data
        skuReference = FirebaseDatabase.getInstance().getReference("SKU");
        targetReference= FirebaseDatabase.getInstance().getReference("TARGET");
        salesMenRefernce=FirebaseDatabase.getInstance().getReference("SALESMAN_USERNAME");
        //synchronizing the database for the offline use
        skuReference.keepSynced(true);
        targetReference.keepSynced(true);
        salesMenRefernce.keepSynced(true);

        //relative layouts
        rellay1 = view.findViewById(R.id.target_edit_layout);
        rellay2=view.findViewById(R.id.adding_sku_rellay2_edit);
        rally3 = view.findViewById(R.id.adding_target_bottom_edit);
        main_view=view.findViewById(R.id.main_view);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        headerIndicator=(ImageView)view.findViewById(R.id.headerIndicator);
                        expansionLayout=(ExpansionLayout) view.findViewById(R.id.expansionLayout);

                       // if(headerIndicator.isPressed())
                            {
                                //expansionLayout.setVisibility(View.VISIBLE);
                            }
                            //else{
                             //   expansionLayout.setVisibility(View.GONE);
                         //   }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        ViewGroup viewGroup = view.findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_target_dialog_layout, viewGroup, false);

                        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setView(dialogView);

                        salesman_edit_target = (TextView) dialogView.findViewById(R.id.salesman_edit_target_details);
                        target_edit_target = (TextView) dialogView.findViewById(R.id.target_edit_target_details);
                        start_date_edit_target=(TextView) dialogView.findViewById(R.id.start_date_edit_target_details);
                        end_date_edit_target=(TextView) dialogView.findViewById(R.id.end_date_edit_target_details);
                        //sku
                        company_sku_expandable= (TextView) dialogView.findViewById(R.id.company_sku_expandable);
                        category_sku_expandable= (TextView) dialogView.findViewById(R.id.category_sku_expandable);
                        product_name_sku_expandable= (TextView) dialogView.findViewById(R.id.product_name_sku_expandable);
                        product_size_sku_expandable= (TextView) dialogView.findViewById(R.id.product_size_sku_expandable);


                        salesman_edit_target.setText(targetList.get(position).getSalesmenEmail());
                        target_edit_target.setText(String.valueOf(  targetList.get(position).getTARGET()));
                        start_date_edit_target.setText(targetList.get(position).getStartDateString());
                        end_date_edit_target.setText(targetList.get(position).getEndDateString());
//sku
                        company_sku_expandable.setText(targetList.get(position).getSKU().getCompany().getName());
                        category_sku_expandable.setText(targetList.get(position).getSKU().getCatagory().getName());
                        product_name_sku_expandable.setText(targetList.get(position).getSKU().getProductName());
                        product_size_sku_expandable.setText(String.valueOf(targetList.get(position).getSKU().getSize()));

                        final AlertDialog alertDialog = builder.create();

                        Button dialog_yes_Button = (Button)dialogView.findViewById(R.id.edit_target_button);
                        dialog_yes_Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();

                            }
                        }); Button dialog_no_Button = (Button)dialogView.findViewById(R.id.cancel_edit_target_button);
                        dialog_no_Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                //Fragment fragment=new View_Today_Report_Fragment();
                            }
                        });

                        alertDialog.show();
                    }
                })
        );

        //progress bar
        progressBar = view.findViewById(R.id.edit_fragment_my_progress_bar);
        progressBarh.postDelayed(runnable1, 0);

        //setting the array adapters
        skuArrayAdapter = new ArrayAdapter(getContext(),  R.layout.spinner_text, skuList);
        skuArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        salesmanIdArrayAdapter=new ArrayAdapter<>(getContext(),R.layout.spinner_text,salesmanIdList);
        salesmanIdArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        add_target_et= view.findViewById(R.id.edit_target_et);

       /* add_target_button =  view.findViewById(R.id.edit_target_button);
        add_target_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sku sku = (Sku) skuSpinner.getSelectedItem();
                SalesmanId salesmanId=(SalesmanId) salesmenSpinner.getSelectedItem();
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
                Target target=new Target(id,sku,sku.getId(),salesmanId.getEmail(),add_target,startDateString,endDateString);
                if(id!=null)
                {
                    targetReference.child(id).setValue(target);
                    add_target_et.setText("");
                }
                else
                {
                    Toast.makeText(getContext(),
                            "Error \n string id=null \n contact developer immediately",
                            Toast.LENGTH_SHORT).show();
                }

                progressBarh.postDelayed(runnable1,500);
            }
        });*/

        return  view;
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
                recyclerViewAdapter=new RecyclerViewAdapter(targetList, (Activity) getContext());
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}