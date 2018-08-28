package com.example.ahuja.dietanalyzer;

import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DataShowFrag;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;

public class MyFoodAddSearch extends AppCompatActivity {
    Bundle bundle;
    Cursor cursor;
    DbOpenHelper dbOpenHelper;
    String cquery;
    TextInputEditText query;
    DataShowFrag my_food;
    boolean searchpres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_add_search);
        bundle=getIntent().getExtras();
        dbOpenHelper=new DbOpenHelper(this);
        cursor=dbOpenHelper.Fetch_Database(Contract.DbFood.TableName);
        cquery="no query";
        query=findViewById(R.id.my_food_add_search_search);
        my_food=(DataShowFrag)getSupportFragmentManager().findFragmentById(R.id.fragment_my_food_add_search);
        my_food.setContext(this);
        my_food.setCurr_date(bundle.getString("cdate_store",new DateHandler().getDate_store()));
        my_food.setCdate_show(bundle.getString("cdate_show",new DateHandler().getDate_show()));
        my_food.setCtype_of_meal(bundle.getString("ctype_of_meal","Breakfast"));
        my_food.setView(findViewById(R.id.my_food_add_search_relative_layout));
    }

    @Override
    protected void onPostResume(){
        my_food.setPath("MyFoodAddSearch");
        my_food.setCursor();
        super.onPostResume();
    }

    public void SearchMyFood(View view){
        cquery=query.getText().toString();
        cursor=dbOpenHelper.Fetch_Database(cquery, Contract.DbFood.TableName);
        searchpres=my_food.Cursor_Changed(cursor);
    }
}
