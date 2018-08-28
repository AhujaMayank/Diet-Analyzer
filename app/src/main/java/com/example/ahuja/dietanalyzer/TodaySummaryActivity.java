package com.example.ahuja.dietanalyzer;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;

import java.awt.font.TextAttribute;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodaySummaryActivity extends AppCompatActivity {

    Bundle bundle;
    DecimalFormat decimalFormat;
    DbOpenHelper dbOpenHelper;
    Cursor cursor;
    //Date Card
    TextView date_show;
    DateHandler dateHandler;
    String cdate_show,cdate_store;
    String date[];

    //nutrition data card
    TextView cal_count,carbs,fats,proteins,waterintake;
    float ccal_count,ccarbs,cfats,cproteins,cwaterintake;
    ProgressBar calprogress;

    //Exercise card
     TextView cal_burnt,active_time;
     float ccal_burnt,cactive_time;
     ImageView eximage_show;

     //Weight card
     TextView weight,change;
     float cweight,cchange;
     ImageView wtimage_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_summary);

        decimalFormat=new DecimalFormat("#.##");
        dbOpenHelper=new DbOpenHelper(this);
        bundle=getIntent().getExtras();


        date_show=findViewById(R.id.daily_curr_date);
        dateHandler=new DateHandler();
        if(bundle!=null){
            cdate_store=bundle.getString("cdate_store",new DateHandler().getDate_store());
            cdate_show=bundle.getString("cdate_show",new DateHandler().getDate_show());
        }
        else {
            cdate_show = dateHandler.getDate_show();
            cdate_store = dateHandler.getDate_store();
        }
        date=cdate_store.split("/");

        cal_count=findViewById(R.id.daily_calories_text);
        carbs=findViewById(R.id.daliy_text_carbs);
        fats=findViewById(R.id.daily_text_fats);
        proteins=findViewById(R.id.daily_text_proteins);
        waterintake=findViewById(R.id.daily_text_waterIntake);
        calprogress=findViewById(R.id.daily_progress_calories);

        cal_burnt=findViewById(R.id.daily_text_ex_cal_burnt);
        active_time=findViewById(R.id.daily_text_ex_active_time);
        eximage_show=findViewById(R.id.daily_image_exercise);
        eximage_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddExercise.class);
                intent.putExtra("cdate_show",cdate_show);
                intent.putExtra("cdate_store",cdate_store);
                startActivity(intent);
            }
        });
        weight=findViewById(R.id.daily_text_weight);
        change=findViewById(R.id.daliy_text_weight_changechange);
        wtimage_show=findViewById(R.id.daily_image_weight);
        wtimage_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddWeight.class);
                intent.putExtra("cdate_show",cdate_show);
                intent.putExtra("cdate_store",cdate_store);
                cursor=dbOpenHelper.Fetch_Database(date[2],date[1],date[0], Contract.DbWeight.TableName);
                cursor.moveToFirst();
                if(!cursor.isAfterLast()){
                    intent.putExtra("IsTodayWtRecorded",true);
                    intent.putExtra("cWeight",cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight)));
                    intent.putExtra("cBodyFat",cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Body_Fat)));
                    intent.putExtra("cMuscle",cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Body_Muscle)));
                    intent.putExtra("Col_ID",cursor.getLong(cursor.getColumnIndex(Contract.DbWeight._ID)));
                }
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPostResume() {
        setdate();
        setexercise();
        setnutrition();
        setweight();
        super.onPostResume();
    }

    //Buttons OnClick
    public void Prev_Date(View view){
        dateHandler.decrementDate();
        cdate_store=dateHandler.getDate_store();
        cdate_show=dateHandler.getDate_show();
        onPostResume();

    }
    public void Next_Date(View view){
        dateHandler.incrementDate();
        cdate_store=dateHandler.getDate_store();
        cdate_show=dateHandler.getDate_show();
        onPostResume();
    }
    public void AddMealBreakfast(View view){
        Intent intent=new Intent(this,AddMealOption.class);
        intent.putExtra("Path","TodaySummary");
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal","Breakfast");
        startActivity(intent);
    }
    public void AddMealLunch(View view){
        Intent intent=new Intent(this,AddMealOption.class);
        intent.putExtra("Path","TodaySummary");
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal","Lunch");
        startActivity(intent);
    }
    public void AddMealDinner(View view){
        Intent intent=new Intent(this,AddMealOption.class);
        intent.putExtra("Path","TodaySummary");
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal","Dinner");
        startActivity(intent);
    }
    public void AddMealMorningSnack(View view){
        Intent intent=new Intent(this,AddMealOption.class);
        intent.putExtra("Path","TodaySummary");
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal","Morning Snack");
        startActivity(intent);
    }
    public void AddMealAfternoonSnack(View view){
        Intent intent=new Intent(this,AddMealOption.class);
        intent.putExtra("Path","TodaySummary");
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal","Afternoon Snack");
        startActivity(intent);
    }
    public void AddMealEveningSnack(View view){
        Intent intent=new Intent(this,AddMealOption.class);
        intent.putExtra("Path","TodaySummary");
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("ctype_of_meal","Evening Snack");
        startActivity(intent);
    }
    public void AddWaterIntake(View view){
        Intent intent=new Intent(view.getContext(),AddWater.class);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("cdate_store",cdate_store);
        startActivity(intent);
    }
    //set Data methods
    public void setdate(){
              date_show.setText(cdate_show);
    }
    public void setnutrition(){
        date=cdate_store.split("/");
        cursor=dbOpenHelper.Fetch_Database(date[2],date[1],date[0], Contract.Dbnutrition.TableName);
        ccal_count=0;
        ccarbs=0;
        cfats=0;
        cproteins=0;
        cwaterintake=0;
        if(cursor!=null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ccal_count += cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie));
                ccarbs += cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Carbs));
                cfats += cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Fats));
                cproteins += cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Proteins));
                cursor.moveToNext();
            }
        }
           if(ccal_count<2000)
                 calprogress.setProgress((int)Float.parseFloat(String.valueOf(ccal_count)));
           else
                 calprogress.setProgress(2000);
          cursor=dbOpenHelper.Fetch_Database(date[2],date[1],date[0], Contract.DbWater.TableName);
          if(cursor!=null){
              cursor.moveToFirst();
              while (!cursor.isAfterLast()){
                  cwaterintake+=cursor.getFloat(cursor.getColumnIndex(Contract.DbWater.Col_Intake_Ml));
                  cursor.moveToNext();
              }
          }

           cal_count.setText("Calories : "+decimalFormat.format(ccal_count)+" Kcal");
           waterintake.setText(String.valueOf(cwaterintake));
          carbs.setText("Carbs : "+decimalFormat.format(ccarbs)+" g");
           fats.setText("Fats : "+decimalFormat.format(cfats)+" g");
           proteins.setText("Proteins : "+decimalFormat.format(cproteins)+" g");
           //waterintake.setText(String.valueOf(cwaterintake));
    }
    public void setexercise(){
        date=cdate_store.split("/");
        cursor=dbOpenHelper.Fetch_Database(date[2],date[1],date[0], Contract.DbExercise.TableName);
        ccal_burnt = 0;

        if(cursor!=null) {
            cursor.moveToFirst();
             while (!cursor.isAfterLast()) {
                ccal_burnt += cursor.getFloat(cursor.getColumnIndex(Contract.DbExercise.Col_Calorie_Burnt));
                cursor.moveToNext();
            }
        }
        if(ccal_burnt!=0){
         cal_burnt.setText(String.valueOf(ccal_burnt)+" Kcal");
         active_time.setVisibility(View.VISIBLE);
         active_time.setText("Active Time : "+decimalFormat.format(Math.floor(ccal_burnt/5.5))+" mins");
        }
        else {
            cal_burnt.setText("Be more active!! Click on Image to record now.");
            active_time.setVisibility(View.INVISIBLE);

        }
    }
    public void setweight(){
        date=cdate_store.split("/");
        cursor=dbOpenHelper.Fetch_Database(Contract.DbWeight.TableName);
        cweight=0;
        float max=0,min=0;
        if(cursor!=null) {
            cursor.moveToNext();
            while (!cursor.isAfterLast()) {
                cweight = cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight));
                if (min == 0 && max == 0) {
                    max = min = cweight;
                }
                if (max < cweight) {
                    max = cweight;
                }
                if (min > cweight) {
                    min = cweight;
                }
                cursor.moveToNext();
            }

        cursor=dbOpenHelper.Fetch_Database(date[2],date[1],date[0],Contract.DbWeight.TableName);
        cursor.moveToFirst();
        cweight=0;
        if(!cursor.isAfterLast())
              cweight=cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight));
            cchange=max-cweight;
        if(cweight!=0){
            change.setVisibility(View.VISIBLE);
            change.setText("Change(so Far) : "+decimalFormat.format(cchange)+" Kg");
            weight.setText("Current : "+decimalFormat.format(cweight)+" Kg");
        }
        else{
            weight.setText("No Weight Record! Click on Image to record now.");
            change.setVisibility(View.INVISIBLE);
        }
        }

    }
}
