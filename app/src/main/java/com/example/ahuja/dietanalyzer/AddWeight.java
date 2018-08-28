package com.example.ahuja.dietanalyzer;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Toast;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.Database.Weight_Object;

import java.text.DecimalFormat;

public class AddWeight extends AppCompatActivity {
    DecimalFormat decimalFormat;
    AppCompatTextView date;
    DateHandler dateHandler;
    DbOpenHelper dbOpenHelper;
    boolean flag;
    String cdate[];
    String cdate_show;
    Weight_Object weight_object;
    TextInputEditText weight,bodyfat,musclemass;
    float cweight,cbodyfat,cmusclemass;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decimalFormat=new DecimalFormat("#.##");
        flag=false;
        id=-1;
        setContentView(R.layout.activity_add_weight);
        dateHandler=new DateHandler();
        dbOpenHelper=new DbOpenHelper(this);
        date=findViewById(R.id.add_weight_date);
        weight=findViewById(R.id.add_weight_weight);
        bodyfat=findViewById(R.id.add_weight_body_fat);
        musclemass=findViewById(R.id.add_weight_muscle);
        setdata();

    }
    //Buttons
    public void Save_Weight(View v){
        getdata();
        weight_object = new Weight_Object(cdate[2], cdate[1], cdate[0], cweight, cbodyfat, cmusclemass);
        if(flag&&id!=-1)
          dbOpenHelper.DeleteEntry(id, Contract.DbWeight.TableName);
        dbOpenHelper.insert_weight_data(weight_object);
        Intent intent=new Intent(this,TodaySummaryActivity.class);
        intent.putExtra("cdate_show",cdate_show);
        intent.putExtra("cdate_store",cdate[0]+"/"+cdate[1]+"/"+cdate[2]);

        startActivity(intent);
    }
    public void Discard_Weight(View v){}

    //View Setters
    public void setdata(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            cdate = bundle.getString("cdate_store", new DateHandler().getDate_store()).split("/");
            cdate_show = bundle.getString("cdate_show", new DateHandler().getDate_show());
            cweight=bundle.getFloat("cWeight",0);
            cmusclemass=bundle.getFloat("cMuscle",0);
            cbodyfat=bundle.getFloat("cBodyFat",0);
            flag=bundle.getBoolean("IsTodayWtRecorded",false);
            id=bundle.getLong("Col_ID",-1);

        }
        else{
            cdate=new DateHandler().getDate_store().split("/");
            cdate_show=new DateHandler().getDate_show();
        }
        if(cweight!=0)
            weight.setText(decimalFormat.format(cweight));
        if(cmusclemass!=0)
            musclemass.setText(decimalFormat.format(cmusclemass));
        if(cbodyfat!=0)
            bodyfat.setText(decimalFormat.format(cbodyfat));

        date.setText(cdate_show);
    }
    public void getdata(){
        cweight=Float.parseFloat(weight.getText().toString());
        cbodyfat= Float.parseFloat(bodyfat.getText().toString());
        cmusclemass=Float.parseFloat(musclemass.getText().toString());
    }

}
