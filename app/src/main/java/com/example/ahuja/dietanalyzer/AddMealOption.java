package com.example.ahuja.dietanalyzer;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DataShowFrag;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.Recycler_View_Fragment;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;

public class AddMealOption extends AppCompatActivity {
    //date data
    AppCompatTextView date;
    String cdate_show,cdate_store;
    AppCompatTextView Type_Of_Meal;
    String ctype_of_meal;
    DataShowFrag fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_option);
        date=findViewById(R.id.add_meal_option_date);
        Type_Of_Meal=findViewById(R.id.add_meal_option_type_of_meal);
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        fragment= (DataShowFrag) fragmentManager.findFragmentById(R.id.fragment_data_show_frag_layout);
        fragment.setPath("AddMealOption");
        fragment.setContext(this);
        fragment.setView(findViewById(R.id.add_meal_option_relative_layout));
    }

    @Override
    protected void onPostResume() {
        setData();
        super.onPostResume();
    }
    public void AddWaterOption(View view){
        Intent intent=new Intent(view.getContext(),MyFoodAddSearch.class);
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal",ctype_of_meal);
        startActivity(intent);
    }
    public void AddMealOptSearch(View view){
        Intent intent=new Intent(this,SearchItem.class);
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal",ctype_of_meal);
        startActivity(intent);
    }
    public void AddMealOptManual(View view){
        Intent intent=new Intent(this,AddMealManual.class);
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal",ctype_of_meal);
        startActivity(intent);
    }

    public void setData(){
        Bundle bundle=getIntent().getExtras();
        cdate_show=bundle.getString("cdate_show",new DateHandler().getDate_show());
        cdate_store=bundle.getString("cdate_store",new DateHandler().getDate_store());
        ctype_of_meal=bundle.getString("ctype_of_meal");
        fragment.setCurr_date(cdate_store);
        fragment.setCdate_show(cdate_show);
        fragment.setCtype_of_meal(ctype_of_meal);
        date.setText(cdate_show);
        Type_Of_Meal.setText(ctype_of_meal);
    }
}
