package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ahuja.dietanalyzer.AddMeal;
import com.example.ahuja.dietanalyzer.Connect_API.ApiInterface;
import com.example.ahuja.dietanalyzer.Connect_API.Model.HintsJson;
import com.example.ahuja.dietanalyzer.Connect_API.Model.MainJson;
import com.example.ahuja.dietanalyzer.Connect_API.QueryHandler;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.MainActivity;
import com.example.ahuja.dietanalyzer.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recycler_View_Fragment extends Fragment {
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    Rv_Adapter rv_adapter;
    LinearLayoutManager linearLayoutManager;
    String curr_date,cdate_show,ctype_of_meal;
    MainJson mainJson=null;
    String path;

    public void setPath(String path) {
        this.path = path;
    }
    public void setMainJson(MainJson mainJson) {
        this.mainJson = mainJson;
    }
    public void setCurr_date(String curr_date) {
        this.curr_date = curr_date;
    }
    public void setCtype_of_meal(String ctype_of_meal) {
        this.ctype_of_meal = ctype_of_meal;
    }
    public void setCdate_show(String cdate_show) {
        this.cdate_show = cdate_show;
    }

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.recycler_view_fragment,container,false);
    }


    @Override
    public void onResume() {
        linearLayoutManager = new LinearLayoutManager(getView().getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        rv_adapter = new Rv_Adapter(getView().getContext());
        rv_adapter.setPath(path);

        if (mainJson != null)
            rv_adapter.setHints(mainJson.getHints());
        rv_adapter.setListener(new Rv_Adapter.ListItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view, HintsJson hintsJson) {
                HintsJson hintsJsons = mainJson.getHints().get(position);

                Intent intent = new Intent(getContext(), AddMeal.class);
                intent.putExtra("Path", "SearchActivity");
                intent.putExtra("Label", hintsJsons.getFood().getLabel());
                if (hintsJson.getFood().getNutrients().getENERC_KCAL() != null)
                    intent.putExtra("Calories", hintsJsons.getFood().getNutrients().getENERC_KCAL());
                if (hintsJson.getFood().getNutrients().getCHOCDF() != null)
                    intent.putExtra("Carbs", hintsJsons.getFood().getNutrients().getCHOCDF());
                if (hintsJsons.getFood().getNutrients().getFAT() != null)
                    intent.putExtra("Fats", hintsJsons.getFood().getNutrients().getFAT());
                if (hintsJsons.getFood().getNutrients().getPROCNT() != null)
                    intent.putExtra("Proteins", hintsJsons.getFood().getNutrients().getPROCNT());
                intent.putExtra("cdate_show",cdate_show);
                intent.putExtra("cdate_store", curr_date);
                intent.putExtra("ctype_of_meal", ctype_of_meal);
                startActivity(intent);

            }
        });

        recyclerView.setAdapter(rv_adapter);

        super.onResume();
    }


}
