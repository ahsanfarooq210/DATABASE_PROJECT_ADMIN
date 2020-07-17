package com.example.database_project_admin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.database_project_admin.Entity.Orders;
import com.example.database_project_admin.Entity.SalesmanId;
import com.example.database_project_admin.Entity.SkuCatagory;
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


public class show_order_filter_catagory extends Fragment
{

    private Spinner catagorySpinner;
    private Button searchBtn;
    private DatabaseReference catagory;
    private List<SkuCatagory> skuCatagoryList;
    private ArrayAdapter<SkuCatagory> catagoryArrayAdapter;
    private List<Orders> orderList;
    private RecyclerView recyclerView;



    public show_order_filter_catagory()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_show_order_filter_catagory, container, false);


        catagorySpinner=v.findViewById(R.id.catagory_filter_spinner);
        searchBtn=v.findViewById(R.id.catagory_serch_btn);
        recyclerView=v.findViewById(R.id.filter_catagory_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        catagory= FirebaseDatabase.getInstance().getReference("CATAGORY");
        skuCatagoryList=new ArrayList<>();
        orderList=new ArrayList<>();
        catagoryArrayAdapter=new ArrayAdapter(getContext(),R.layout.spinner_text,skuCatagoryList);
        catagoryArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);

        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SkuCatagory catagory= (SkuCatagory) catagorySpinner.getSelectedItem();
                Query q=FirebaseDatabase.getInstance().getReference().child("ORDERS").orderByChild("catagory_id").equalTo(catagory.getId());
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
        catagory.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                skuCatagoryList.clear();
                for(DataSnapshot catagory:dataSnapshot.getChildren())
                {
                    skuCatagoryList.add(catagory.getValue(SkuCatagory.class));
                }
                catagorySpinner.setAdapter(catagoryArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getContext(), "Error in downloading the catagories", Toast.LENGTH_SHORT).show();
            }
        });

    }
}