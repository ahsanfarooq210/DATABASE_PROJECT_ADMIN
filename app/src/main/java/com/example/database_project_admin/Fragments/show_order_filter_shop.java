package com.example.database_project_admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_project_admin.Entity.Orders;
import com.example.database_project_admin.R;
import com.example.database_project_admin.Entity.ShopDetails;
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
public class show_order_filter_shop extends Fragment
{

    private Spinner shopSpinner;
    private Button searchBtn;
    private DatabaseReference shopReference;
    private List<ShopDetails> shopDetailsList;
    private ArrayAdapter<ShopDetails> shopDetailsArrayAdapter;
    private List<Orders> orderList;
    private RecyclerView recyclerView;

    public show_order_filter_shop()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_order_filter_shop, container, false);


        shopSpinner = v.findViewById(R.id.shop_filter_spinner);
        searchBtn = v.findViewById(R.id.shop_serch_btn);
        shopReference = FirebaseDatabase.getInstance().getReference("SHOP");
        shopDetailsList = new ArrayList<>();
        shopReference.keepSynced(true);

        shopDetailsArrayAdapter = new ArrayAdapter(getContext(), R.layout.spinner_text, shopDetailsList);
        shopDetailsArrayAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        orderList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.filter_shop_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final ShopDetails shopDetails= (ShopDetails) shopSpinner.getSelectedItem();


                Query query=FirebaseDatabase.getInstance().getReference().child("ORDERS").orderByChild("shop_id").equalTo(shopDetails.getId());

                query.addListenerForSingleValueEvent(new ValueEventListener()
                {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        orderList.clear();
                        for(DataSnapshot shop:dataSnapshot.getChildren())
                        {
                           // if(shop.getValue(Orders.class).getShop().getId().equals(shopDetails.getId()))
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

        shopReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                shopDetailsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    shopDetailsList.add(snapshot.getValue(ShopDetails.class));
                }
                shopSpinner.setAdapter(shopDetailsArrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getContext(), "Error in downloading the shops", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
