package com.example.database_project_admin.BrodcastReceivers;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class LocationBrodcast extends BroadcastReceiver
{
    public static boolean status;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ContentResolver contentResolver=context.getContentResolver();
        int mode= Settings.Secure.getInt(contentResolver,Settings.Secure.LOCATION_MODE,Settings.Secure.LOCATION_MODE_OFF);
        Toast.makeText(context,""+mode,Toast.LENGTH_SHORT).show();
        if(mode==Settings.Secure.LOCATION_MODE_OFF)
        {
            status=false;
            //Toast.makeText(context,"flag = false",Toast.LENGTH_SHORT).show();
        }
        else
        {

            status=true;
           // Toast.makeText(context,"flag = true",Toast.LENGTH_SHORT).show();

        }
    }
}
