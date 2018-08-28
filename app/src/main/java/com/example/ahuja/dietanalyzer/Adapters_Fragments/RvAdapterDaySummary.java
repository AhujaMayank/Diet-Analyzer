package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.R;
import com.example.ahuja.dietanalyzer.WeekSummary;

import java.util.ArrayList;
import java.util.Calendar;

public class RvAdapterDaySummary extends RecyclerView.Adapter<RvAdapterDaySummary.DayViewHolder> {
    boolean flag=false;
    ArrayList<String> Dates;
    ArrayList<Float> Weight;
    ArrayList<Float> Calories;
    ArrayList<Float> BodyFat;
    ArrayList<Float> Muscle;
    //ArrayList<Float> Water;

    public void SetData(boolean flag, ArrayList<String> dates, ArrayList<Float> weight,
                               ArrayList<Float> calories, ArrayList<Float> bodyFat, ArrayList<Float> muscle ) {
        this.flag = flag;
        Dates = dates;
        Weight = weight;
        Calories = calories;
        BodyFat = bodyFat;
        Muscle = muscle;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.day_summary_rv_architecture,parent,false));
    }

    @Override
    public int getItemCount() {
        if(flag)
            return Dates.size();
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
            holder.currdate.setText(Dates.get(position));
            holder.calorie.setText("Net Calories : "+String.valueOf(Calories.get(position))+" Kcal");
            holder.muscle.setText("Muscle Mass : "+String.valueOf(Muscle.get(position)+" Kg"));
            holder.bodyfat.setText("Body Fat : "+String.valueOf(BodyFat.get(position))+" %");
            holder.weight.setText("Weight : "+String.valueOf(Weight.get(position))+" Kg");
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        public TextView currdate,calorie,weight,bodyfat,muscle;
        public ImageView calpn;

        public DayViewHolder(View itemView) {
            super(itemView);
            currdate=itemView.findViewById(R.id.day_summary_rv_curr_date);
            calorie=itemView.findViewById(R.id.day_summary_rv_calorie);
            weight=itemView.findViewById(R.id.day_summary_rv_weight);
            bodyfat=itemView.findViewById(R.id.day_rv_frag_body_fat);
            muscle=itemView.findViewById(R.id.day_summary_rv_muscle);
            calpn=itemView.findViewById(R.id.day_summary_frag_calorie_image);
        }
    }
}
