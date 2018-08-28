package com.example.ahuja.dietanalyzer;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.Database.Exercise_Object;

public class AddExercise extends AppCompatActivity {
    Exercise_Object exercise_object;
    DbOpenHelper dbOpenHelper;
    AppCompatTextView date;
    String cdate_show;
    String cdate_store;
    String[] cdate;
    DateHandler dateHandler;

    TextInputEditText desc,calories;
    String cdesc;
    float ccalories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        dateHandler=new DateHandler();
        date=findViewById(R.id.add_exercise_date);
        desc=findViewById(R.id.add_exercise_desc);
        calories=findViewById(R.id.add_exercise_calories);
        dbOpenHelper=new DbOpenHelper(this);
        setDate();
    }

    public void Save_Exercise(View v){
        getData();
        if(cdesc!=null&&ccalories!=0&&cdate!=null)
             exercise_object=new Exercise_Object(cdate[2] ,cdate[1],cdate[0],cdesc,ccalories);
        long id=dbOpenHelper.insert_exercise_data(exercise_object);
        Intent intent=new Intent(this,TodaySummaryActivity.class);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("cdate_store",cdate_store);
        startActivity(intent);
        //Toast.makeText(getApplicationContext(),"Id Inserted :"+id,Toast.LENGTH_SHORT).show();
    }
    public void Discard_Exercise(View v){}

    public void getData(){
        cdate=cdate_store.split("/");
        cdesc=desc.getText().toString();
        ccalories=Float.parseFloat(calories.getText().toString());
    }
    public void setDate(){
        Bundle bundle=getIntent().getExtras();
        cdate_store=bundle.getString("cdate_store",new DateHandler().getDate_store());
        cdate_show=bundle.getString("cdate_show",new DateHandler().getDate_store());
        date.setText(cdate_show);

    }
}
