package com.example.ahuja.dietanalyzer;

import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.Database.Water_Object;

public class AddWater extends AppCompatActivity {
    TextView water_intake;
    Cursor cursor;
    Water_Object water_object;
    DbOpenHelper dbOpenHelper;
    TextView curr_date;
    long id;
    float water;
    String cdate[],cdate_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water);
        Bundle bundle=getIntent().getExtras();
        cdate=bundle.getString("cdate_store",new DateHandler().getDate_show()).split("/");
        cdate_show=bundle.getString("cdate_store",new DateHandler().getDate_show());
        dbOpenHelper=new DbOpenHelper(this);
        water_intake=findViewById(R.id.add_water_today_intake);
        curr_date=findViewById(R.id.add_water_date);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setData();
    }

    public void AddWaterIntake(View view){
        water+=250;
        water_intake.setText(String.valueOf(water));
        water_object=new Water_Object(cdate[2],cdate[1],cdate[0],(float)0.4,(float)250.0,(float)1.0);
        dbOpenHelper.insert_water_data(water_object);
    }
    public void setData(){

        cursor=dbOpenHelper.Fetch_Database(cdate[2],cdate[1],cdate[0], Contract.DbWater.TableName);
        water=0;
        if(cursor!=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                water+=cursor.getFloat(cursor.getColumnIndex(Contract.DbWater.Col_Intake_Ml));
                cursor.moveToNext();
            }
        }
        curr_date.setText(cdate_show);
        water_intake.setText(String.valueOf(water));
    }
}
