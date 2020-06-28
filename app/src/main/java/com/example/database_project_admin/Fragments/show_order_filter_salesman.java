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

import android.widget.Spinner;
import android.widget.Toast;

import com.example.database_project_admin.Entity.Orders;
import com.example.database_project_admin.R;
import com.example.database_project_admin.Entity.SalesmanId;
import com.example.database_project_admin.RvAdapters.show_order_rv_adaprter;
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
 */
public class show_order_filter_salesman extends Fragment
{
    private Spinner salesmanSpinner;
    private Button searchBtn;
    private DatabaseReference salesmanReference;
    private List<SalesmanId> salesmanIdList;
    private ArrayAdapter <SalesmanId>salesmanAdapter;
    private List<Orders> orderList;
    private RecyclerView recyclerView;


    public show_order_filter_salesman()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_show_order_filter_salesman, container, false);

        salesmanSpinner=v.findViewById(R.id.salesman_filter_spinner);
        searchBtn=v.findViewById(R.id.serch_btn);
        salesmanReference= FirebaseDatabase.getInstance().getReference("SALESMAN_USERNAME");
        salesmanIdList=new ArrayList<>();
        salesmanReference.keepSynced(true);

        salesmanAdapter=new ArrayAdapter(getContext(),R.layout.spinner_text,salesmanIdList);
        salesmanAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        orderList=new ArrayList<>();
        recyclerView=v.findViewById(R.id.filter_salesman_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SalesmanId salesmanId= (SalesmanId) salesmanSpinner.getSelectedItem();

                String salesmanEmail=salesmanId.getEmail();



                Query  query=FirebaseDatabase.getInstance().getReference("ORDERS").orderByChild("salesman").equalTo(salesmanEmail);

                query.addListenerForSingleValueEvent(new ValueEventListener()
                {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        orderList.clear();
                        for(DataSnapshot shop:dataSnapshot.getChildren())
                        {
                            orderList.add(shop.getValue(Orders.class));
                        }
                        show_order_rv_adaprter showOrderRvAdaprter=new show_order_rv_adaprter((ArrayList<Orders>) orderList,  getActivity());
                        recyclerView.setAdapter(showOrderRvAdaprter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(getContext(), "Error in downloading the data", Toast.LENGTH_SHORT).show();
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

        salesmanReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                salesmanIdList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    salesmanIdList.add(snapshot.getValue(SalesmanId.class));
                }
                salesmanSpinner.setAdapter(salesmanAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getContext(), "Error in downloading the data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
