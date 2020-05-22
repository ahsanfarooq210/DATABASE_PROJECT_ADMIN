package com.example.database_project_admin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.database_project_admin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class show_order_filter_sku extends Fragment
{

    public show_order_filter_sku()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_order_filter_sku, container, false);
    }
}
