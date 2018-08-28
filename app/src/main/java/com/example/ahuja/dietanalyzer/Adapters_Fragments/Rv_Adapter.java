package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Connect_API.Model.FoodJson;
import com.example.ahuja.dietanalyzer.Connect_API.Model.HintsJson;
import com.example.ahuja.dietanalyzer.Connect_API.Model.MainJson;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.R;

import java.text.DecimalFormat;
import java.util.List;

public class Rv_Adapter extends RecyclerView.Adapter<Rv_Adapter.Rv_ViewHolder> {

    List<HintsJson> hints;
    private Context context;
    private ListItemClickListener listener;

     private String path;
     private String cdate_show;
     private float carbs=0,cal=0,prot=0,fats=0;
     private String item;

    public void setPath(String path) {
        this.path = path;
    }

    public void setHints(List<HintsJson> hints) {
        this.hints = hints;
    }

    public Rv_Adapter(Context context) {
        this.context = context;
    }

    public void setListener(ListItemClickListener listener) {
        this.listener = listener;
    }

    public interface ListItemClickListener{
        void onItemClickListener(int position, View view,HintsJson hintsJson);
    }


    @Override
    public Rv_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.recyclerview_item_architecture,parent,false);
         return new Rv_ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull Rv_ViewHolder holder, int position) {
                        BindHintsResponse(holder,position);

        }
    @Override
    public int getItemCount() {
        if(path.equals("RetroResponse")&&hints!=null)
          return hints.size();
        return 0;
    }

    public class Rv_ViewHolder extends RecyclerView.ViewHolder {
        public  TextView item_name,cal_count,carbs,fats,proteins,date;
        public Rv_ViewHolder(final View itemView) {
            super(itemView);
            item_name=itemView.findViewById(R.id.rv_item_item_name);
            cal_count=itemView.findViewById(R.id.rv_item_cal_count);
            carbs=itemView.findViewById(R.id.rv_item_carbs);
            fats=itemView.findViewById(R.id.rv_item_fats);
            proteins=itemView.findViewById(R.id.rv_item_proteins);
            date=itemView.findViewById(R.id.rv_item_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                         listener.onItemClickListener(getAdapterPosition(),v,hints.get(getAdapterPosition()));
                }
            });
        }
    }

    public void BindHintsResponse(Rv_ViewHolder holder,int position){
         item=hints.get(position).getFood().getLabel();
         carbs=0;
         cal=0;
         prot=0;
         fats=0;
        if(hints.get(position).getFood().getNutrients().getCHOCDF()!=null)
            carbs=Float.parseFloat(hints.get(position).getFood().getNutrients().getCHOCDF());
        if(hints.get(position).getFood().getNutrients().getFAT()!=null)
            fats=Float.parseFloat(hints.get(position).getFood().getNutrients().getFAT());
        if(hints.get(position).getFood().getNutrients().getENERC_KCAL()!=null)
            cal=Float.parseFloat(hints.get(position).getFood().getNutrients().getENERC_KCAL());
        if(hints.get(position).getFood().getNutrients().getPROCNT()!=null)
            prot=Float.parseFloat(hints.get(position).getFood().getNutrients().getPROCNT());
        holder.date.setVisibility(View.INVISIBLE);
        holder.item_name.setText(item);
        holder.cal_count.setText(String.valueOf(cal));
        holder.carbs.setText(String .valueOf(carbs));
        holder.fats.setText(String .valueOf(fats));
        holder.proteins.setText(String.valueOf(prot));
    }



}
