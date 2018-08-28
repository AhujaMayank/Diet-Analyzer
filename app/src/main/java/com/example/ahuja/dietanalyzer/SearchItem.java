package com.example.ahuja.dietanalyzer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.Recycler_View_Fragment;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.Rv_Adapter;
import com.example.ahuja.dietanalyzer.Connect_API.ApiInterface;
import com.example.ahuja.dietanalyzer.Connect_API.Model.HintsJson;
import com.example.ahuja.dietanalyzer.Connect_API.Model.MainJson;
import com.example.ahuja.dietanalyzer.Connect_API.QueryHandler;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchItem extends AppCompatActivity{
    TextInputEditText csearch_item;
    QueryHandler queryHandler;
    MainJson mainJson=null;
    Recycler_View_Fragment Rv_frag;
    String date;
    String ctype_of_meal;

    ApiInterface apiInterface;
    Call<MainJson> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryHandler=new QueryHandler();
        setContentView(R.layout.activity_search_item);
        csearch_item=findViewById(R.id.search_item_editText);

        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        Rv_frag=(Recycler_View_Fragment)fragmentManager.findFragmentById(R.id.fragment_search_item);
        Rv_frag.progressBar=findViewById(R.id.search_frag_pb);
        Rv_frag.recyclerView =findViewById(R.id.rv_fragment_rv);

        Bundle bundle=getIntent().getExtras();
        date=bundle.getString("cdate_store");
        ctype_of_meal=bundle.getString("ctype_of_meal");
        Rv_frag.setCdate_show(bundle.getString("cdate_show"));
        Rv_frag.setCurr_date(date);
        Rv_frag.setCtype_of_meal(ctype_of_meal);
        Rv_frag.setPath("RetroResponse");
        apiInterface = queryHandler.getClient().create(ApiInterface.class);

    }

    public void search_item_onClick(View view){
        if(csearch_item.getText().equals("")){
            Toast.makeText(this,"Enter Item",Toast.LENGTH_SHORT).show();
        }
        else{
            Rv_frag.progressBar.setVisibility(View.VISIBLE);
            Rv_frag.recyclerView.setVisibility(View.INVISIBLE);
            makeRequest();
        }
    }
    public void makeRequest(){
        csearch_item.getText().toString().replace(" ","%2D");
        call = apiInterface.getQueryResult(queryHandler.getApi_id(),queryHandler.getApi_key(),
                csearch_item.getText().toString());
        call.enqueue(new Callback<MainJson>() {
            @Override
            public void onResponse(Call<MainJson> call, Response<MainJson> response) {
                if(response.isSuccessful()){
                    Rv_frag.progressBar.setVisibility(View.INVISIBLE);
                    Rv_frag.recyclerView.setVisibility(View.VISIBLE);
                    mainJson = response.body();
                    Rv_frag.setMainJson(mainJson);
                    Rv_frag.onResume();

                }

            }
            @Override
            public void onFailure(Call<MainJson> call, Throwable t) {
                Log.e("Fail Retrofit", t.toString());
            }


        });

    }


}
