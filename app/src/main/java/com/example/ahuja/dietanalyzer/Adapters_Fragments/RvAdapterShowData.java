package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Connect_API.Model.HintsJson;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.R;

public class RvAdapterShowData extends RecyclerView.Adapter<RvAdapterShowData.ShowDataViewHolder>{
    Cursor cursor;
    Context context;
    String citem_name;
    float ccalories;

    ShowDataListItemClickListener listItemClickListener;


    public RvAdapterShowData(Context context){
        this.context=context;
    }


    public void setListItemClickListener(ShowDataListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public interface ShowDataListItemClickListener{
        void onItemClickListener(int position, View view,Cursor cursor);
    }

    @NonNull
    @Override
    public ShowDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.show_data_rv_fragment_architecture,parent,false);
        return new ShowDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDataViewHolder holder, int position) {
        cursor.moveToPosition(position);
        citem_name=cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Desc));
        ccalories=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie));

        float ccarbs=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Carbs));
        float cfats=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Fats));
        float cproteins=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Proteins));
        float csodium=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Sodium));

        String rat=String .valueOf(calRating(ccalories,ccarbs,cfats,cproteins,csodium));
        if(rat.length()>4)
            rat=rat.substring(0,4);

        holder.rating.setText(rat);
        holder.item_name.setText(citem_name);
        holder.caloriestaken.setText(String.valueOf(ccalories));
    }

    @Override
    public int getItemCount() {
        if(cursor!=null)
            return cursor.getCount();
        return 0;
    }

    public boolean SwapCursor(Cursor ncursor){
        if(ncursor==null)
              return false;
            cursor=ncursor;
            this.notifyDataSetChanged();
        return true;
    }

    public float calRating(float cal,float carb,float fat,float prot,float sod){
        float rat=0;
        if(cal>200)
            rat=+1;
        else if(cal<=200&&cal>125)
            rat+=2;
        else
            rat+=3;
        if(carb>15)
            rat=+1;
        else if(carb<=15&&carb>10)
            rat+=2;
        else
            rat+=3;
        if(fat>10)
            rat=+1;
        else if(cal<=10&&cal>6.5)
            rat+=2;
        else
            rat+=3;
        if(prot>12)
            rat=+3;
        else if(prot<=12&&cal>7)
            rat+=2;
        else
            rat+=1;
        if(sod>400)
            rat=+1;
        else if(sod<400&&sod>200)
            rat+=2;
        else
            rat+=3;
        rat/=3;
        return rat;
    }

    public class ShowDataViewHolder extends RecyclerView.ViewHolder{
        public TextView item_name,caloriestaken,rating;
        public ShowDataViewHolder(View itemView) {
            super(itemView);
            item_name=itemView.findViewById(R.id.rv_show_data_item_name);
            caloriestaken=itemView.findViewById(R.id.rv_show_data_cal_count);
            rating=itemView.findViewById(R.id.rv_data_show_rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClickListener.onItemClickListener(getAdapterPosition(),v,cursor);
                }
            });
        }
    }


}
