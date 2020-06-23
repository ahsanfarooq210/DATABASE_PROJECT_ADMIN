package com.example.database_project_admin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.database_project_admin.Entity.SalesmanId;
import com.example.database_project_admin.R;
import com.example.database_project_admin.RvAdapters.ShowSalesmanRvAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Show_salesman_activity extends AppCompatActivity
{

    //creating a arraylist to store the data from the firebase
    private ArrayList<SalesmanId> salesmanIdArrayList;
    //recycler view to show the data
    private RecyclerView recyclerView;
    //database reference to download the salesmans
    private DatabaseReference shopReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_salesman_activity);

        //initializing the arraylist
        salesmanIdArrayList=new ArrayList<>();

        //initializing the recycler view
        recyclerView=findViewById(R.id.show_salesman_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the database reference
        shopReference= FirebaseDatabase.getInstance().getReference("SALESMAN_USERNAME");

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //downloading the salesman
        shopReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                salesmanIdArrayList.clear();
                for(DataSnapshot salesman:dataSnapshot.getChildren())
                {
                    salesmanIdArrayList.add(salesman.getValue(SalesmanId.class));
                }
                ShowSalesmanRvAdapter showSalesmanRvAdapter=new ShowSalesmanRvAdapter(salesmanIdArrayList,Show_salesman_activity.this);
                recyclerView.setAdapter(showSalesmanRvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(Show_salesman_activity.this, "Error in downloading the salesman", Toast.LENGTH_SHORT).show();
            }
        });
    }
}