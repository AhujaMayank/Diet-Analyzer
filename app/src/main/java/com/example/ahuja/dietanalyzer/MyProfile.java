package com.example.ahuja.dietanalyzer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.FocusFinder;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyProfile extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    DateHandler dateHandler;

    TextInputEditText profile_name,height,weight,goal_weight;
    String cprofile_name;
    float cheight,cweight,cgoal_weight;
    TextView startdate;
    int cstartdayno;
    String cstartdate;
    Button upcan,save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        weight=findViewById(R.id.my_profile_weight);
        height=findViewById(R.id.my_profile_height);
        profile_name=findViewById(R.id.my_profile_name);
        goal_weight=findViewById(R.id.my_profile_goal_weight);
        upcan=findViewById(R.id.my_profile_bt_upcan);
        save=findViewById(R.id.my_profile_bt_sav);
        startdate=findViewById(R.id.my_profile_tv_date);

       // dateHandler=new DateHandler();
        sharedPreferences=getSharedPreferences(String.valueOf(R.string.preference_file_name),Context.MODE_PRIVATE);
        if(sharedPreferences!=null)
             setData();
        else{
             prompt();

        }
    }

    public void MyProfileUpdateCancel(View view){
        if(upcan.getText().toString().equals("Update")){
            save.setVisibility(View.VISIBLE);
            upcan.setText("Cancel");
        }
        else if(upcan.getText().toString().equals("Cancel")){
            upcan.setText("Update");
            save.setVisibility(View.INVISIBLE);
        }
    }
    public void MyProfileSave(View view){
        cprofile_name=profile_name.getText().toString();
        cheight=Float.parseFloat(height.getText().toString());
        cweight=Float.parseFloat(weight.getText().toString());
        cgoal_weight= Float.parseFloat(goal_weight.getText().toString());
     //   setDate();
        SharedPreferences.Editor share=sharedPreferences.edit();
        share.clear();
        share.putFloat("Height",cheight);
        share.putFloat("Weight",cweight);
        share.putFloat("Goal_Weight",cgoal_weight);
        share.putString("Profile_Name",cprofile_name);
        share.putInt("Start_Date",cstartdayno);
        share.commit();
        upcan.setText("Update");
        save.setVisibility(View.INVISIBLE);
        setData();

    }

    public void setData(){
          cheight=sharedPreferences.getFloat("Height",0);
          cweight=sharedPreferences.getFloat("Weight",0);
          cgoal_weight=sharedPreferences.getFloat("Goal_Weight",0);
          cprofile_name=sharedPreferences.getString("Profile_Name","");
          cstartdayno=sharedPreferences.getInt("Start_Date",Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
          if(cheight!=0)
              height.setText(String.valueOf(cheight));
          if(cweight!=0)
               weight.setText(String.valueOf(cweight));
          if(cgoal_weight!=0)
               goal_weight.setText(String.valueOf(cgoal_weight));
          if(cprofile_name!="")
               profile_name.setText(cprofile_name);
          setDate();
          startdate.setText("Start Date "+cstartdate);

    }
    public void prompt(){}

    private void setDate(){
        dateHandler=new DateHandler();
        dateHandler.move_date(-1*((Calendar.getInstance().get(Calendar.DAY_OF_YEAR)-cstartdayno)));
        cstartdate=dateHandler.getDate_store();
    }
    public void ChangeStartDate(View view){
        setDate();
        String[] strings=cstartdate.split("/");
      //  Calendar calendar1=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar= Calendar.getInstance();
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        cstartdayno=calendar.get(Calendar.DAY_OF_YEAR);
                      //  Log.d("Date DD", "onDateSet: "+cstartdayno);
                    }
                },Integer.parseInt(strings[0]),Integer.parseInt(strings[1])-1,Integer.parseInt(strings[2]));
        datePickerDialog.show();

    }
}
