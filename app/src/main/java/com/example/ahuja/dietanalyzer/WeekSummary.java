package com.example.ahuja.dietanalyzer;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DailySummFrag;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class WeekSummary extends AppCompatActivity {
    //View Binding
    TextView date_set;
    TextView calorie_intake,calorie_burnt,exc_cal;
    TextView wt_start,wt_end;
    ImageView imageView;
    String cdate[],cdate_show;
    float ccalorie_intake,ccalorie_burnt;
    float cwt_start,cwt_end;
    int back;

    //Boiler_stuff
    Date date;
    Bundle bundle;
    DbOpenHelper dbOpenHelper;
    Cursor cursor;
    DateHandler dateHandler;

    DailySummFrag dailySummFrag;
    ArrayList<String> Dates;
    ArrayList<Float> Weight;
    ArrayList<Float> Calories;
    ArrayList<Float> BodyFat;
    ArrayList<Float> Muscle;
   // ArrayList<Float> Water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_summary);
        date_set=findViewById(R.id.week_summary_dates_set);
        calorie_intake=findViewById(R.id.week_summary_calorie_intake);
        calorie_burnt=findViewById(R.id.week_summary_calorie_burnt);
        wt_start=findViewById(R.id.week_summary_tv_wt_start);
        wt_end=findViewById(R.id.week_summary_tv_wt_end);
        exc_cal=findViewById(R.id.week_summary_exc_cal);
        imageView=findViewById(R.id.week_summary_image);

        //creating boiler stuff
        bundle=getIntent().getExtras();
        dbOpenHelper=new DbOpenHelper(this);
        dateHandler=new DateHandler();
        date=dateHandler.getDate();
        dailySummFrag= (DailySummFrag) getSupportFragmentManager().findFragmentById(R.id.week_summary_daily_frag);
        Dates=new ArrayList<>();
        Calories=new ArrayList<>();
        Weight=new ArrayList<>();
        Muscle=new ArrayList<>();
        BodyFat=new ArrayList<>();

    }

    @Override
    protected void onPostResume() {
        setData();
        super.onPostResume();
    }


    //Buttons
    public void LastWeekLoad(View view){
        Intent intent=new Intent(view.getContext(),WeekSummary.class);
        intent.putExtra("Back",back+1);
        startActivity(intent);
    }
    public void setData(){
        back=bundle.getInt("Back",0);
        dateHandler.setDateToWeekStart(back);
        cdate_show=dateHandler.getDate_show();
        date=dateHandler.getDate();

        intialiseVariables();
        setVariables();
        dailySummFrag.rvAdapterDaySummary.SetData(true,Dates,Weight,Calories,BodyFat,Muscle);
        cdate_show=cdate_show+" - ";
        cdate_show+=dateHandler.getDate_show();
        date_set.setText(cdate_show);
        wt_start.setText(String.valueOf(cwt_start));
        wt_end.setText(String.valueOf(cwt_end));
        if(cwt_start-cwt_end>0)
            imageView.setImageResource(R.drawable.neg);
        else if(cwt_end-cwt_start>0)
             imageView.setImageResource(R.drawable.pos);
        else
            imageView.setImageResource(R.drawable.dash);
        exc_cal.setText("Exercise : "+ String.valueOf(ccalorie_burnt)+" Kcal");
        ccalorie_burnt+=14000;
        calorie_intake.setText(String.valueOf(ccalorie_intake)+" Kcal");
        calorie_burnt.setText(String.valueOf(ccalorie_burnt)+" Kcal");
    }
    public void intialiseVariables(){
        cwt_start=0;
        cwt_end=0;
        ccalorie_burnt=0;
        ccalorie_intake=0;
    }
    public void setVariables(){
        cdate=dateHandler.getDate_store().split("/");
        float x=0,y=0;
        for(int i=0;i<7;i++){
            cdate=dateHandler.getDate_store().split("/");
            Dates.add(dateHandler.getDate_show());
            x=2000;
            y=0;
            cursor=dbOpenHelper.Fetch_Database(cdate[2],cdate[1],cdate[0], Contract.Dbnutrition.TableName);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                y+=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie));
                ccalorie_intake+=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie));
                cursor.moveToNext();
            }
            cursor=dbOpenHelper.Fetch_Database(cdate[2],cdate[1],cdate[0], Contract.DbExercise.TableName);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                x+=cursor.getFloat(cursor.getColumnIndex(Contract.DbExercise.Col_Calorie_Burnt));
                ccalorie_burnt+=cursor.getFloat(cursor.getColumnIndex(Contract.DbExercise.Col_Calorie_Burnt));
                cursor.moveToNext();
            }
            Calories.add(x-y);
            if(cwt_start==0){
                cursor=dbOpenHelper.Fetch_Database(cdate[2],cdate[1],cdate[0], Contract.DbWeight.TableName);
                cursor.moveToFirst();
                if(!cursor.isAfterLast()){
                    cwt_start=cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight));
                }
            }
                cursor=dbOpenHelper.Fetch_Database(cdate[2],cdate[1],cdate[0], Contract.DbWeight.TableName);
                cursor.moveToFirst();
                if(!cursor.isAfterLast()){
                    //dif(cwt_end==0)
                        cwt_end=cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight));
                    BodyFat.add(cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Body_Fat)));
                    Muscle.add(cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Body_Muscle)));
                    Weight.add(cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight)));
                }
                else{
                    BodyFat.add((float)0);
                    Muscle.add((float)0);
                    Weight.add((float)0);
                }


            dateHandler.incrementDate();
        }
        dateHandler.decrementDate();
    }
}
