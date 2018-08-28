package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ahuja.dietanalyzer.R;

public class DailySummFrag extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayout;
     public RvAdapterDaySummary rvAdapterDaySummary;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rvAdapterDaySummary=new RvAdapterDaySummary();

        return inflater.inflate(R.layout.day_summary_frag,container,false);

    }

    @Override
    public void onResume() {
        recyclerView=getView().findViewById(R.id.day_summary_rv);
        linearLayout=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(rvAdapterDaySummary);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        super.onResume();
    }
}
