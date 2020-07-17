package com.example.database_project_admin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.database_project_admin.Entity.Orders;
import com.example.database_project_admin.Entity.SkuCatagory;
import com.example.database_project_admin.Entity.SkuCompany;
import com.example.database_project_admin.R;
import com.example.database_project_admin.RvAdapters.show_order_rv_adaprter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class show_order_filter_company extends Fragment
{


    private Spinner companySpinner;
    private Button searchBtn;
    private DatabaseReference company;
    private List<SkuCompany> skuCompanyList;
    private ArrayAdapter<SkuCompany> companyArrayAdapter;
    private List<Orders> orderList;
    private RecyclerView recyclerView;

    public show_order_filter_company()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_order_filter_company, container, false);

        recyclerView=v.findViewById(R.id.filter_company_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        companySpinner=v.findViewById(R.id.company_filter_spinner);
        searchBtn=v.findViewById(R.id.company_serch_btn);
        company= FirebaseDatabase.getInstance().getReference("COMPANIES");
        skuCompanyList=new ArrayList<>();
        companyArrayAdapter=new ArrayAdapter(getContext(),R.layout.spinner_text,skuCompanyList);
        companyArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        orderList=new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SkuCompany skuCompany= (SkuCompany) companySpinner.getSelectedItem();
                Query q=FirebaseDatabase.getInstance().getReference().child("ORDERS").orderByChild("company_id").equalTo(skuCompany.getId());
                q.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        orderList.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                            orderList.add(snapshot.getValue(Orders.class));
                        }
                        show_order_rv_adaprter showOrderRvAdaprter=new show_order_rv_adaprter((ArrayList<Orders>) orderList,getActivity());
                        recyclerView.setAdapter(showOrderRvAdaprter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

            }
        });

        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        company.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                skuCompanyList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    skuCompanyList.add(snapshot.getValue(SkuCompany.class));
                }

                companySpinner.setAdapter(companyArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getContext(), "Error in downloading the companies", Toast.LENGTH_SHORT).show();
            }
        });
    }
}