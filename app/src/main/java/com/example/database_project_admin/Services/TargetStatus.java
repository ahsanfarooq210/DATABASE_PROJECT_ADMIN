package com.example.database_project_admin.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.database_project_admin.Target.Entity.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TargetStatus extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        final ArrayList <Target> targetArrayList=new ArrayList<>();
        Query q= FirebaseDatabase.getInstance().getReference().child("TARGET").orderByChild("targetStatus").equalTo("Active");
        q.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                targetArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    targetArrayList.add(snapshot.getValue(Target.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(TargetStatus.this, "Error in downloading the data", Toast.LENGTH_SHORT).show();
            }
        });

        boolean flag = false;
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("TARGET");
        for(Target t:targetArrayList)
        {
            String end=t.getEndDateString();
            try
            {
                Date endDate = convertStringToDate(end);
                Date today=new Date();
                if(endDate.after(today))
                {
                    String id=t.getTARGET_ID();

                    reference.child(id).child("targetStatus").setValue("Inactive");
                }
            } catch (ParseException e)
            {
                e.printStackTrace();
            }




        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
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
}
