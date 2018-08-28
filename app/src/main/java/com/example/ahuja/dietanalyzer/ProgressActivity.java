package com.example.ahuja.dietanalyzer;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DailySummFrag;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.Database.Weight_Object;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;

import org.w3c.dom.EntityReference;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    List<Entry> Entries;

    TextView pointWeight,perdayloss,daysleft;
    float cwtchange,cwtperday;

    DailySummFrag dailySummFrag;
    String[] cdate;
    ArrayList<String> Dates;
    ArrayList<Float> Weight;
    ArrayList<Float> Calories;
    ArrayList<Float> BodyFat;
    ArrayList<Float> Muscle;

    int nod;
    SharedPreferences sharedPreferences;
    DbOpenHelper dbOpenHelper;
    Cursor cursor;
    DateHandler dateHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        lineChart=findViewById(R.id.lc_progress_activity);
        pointWeight=findViewById(R.id.tv_progress_activity);
        perdayloss=findViewById(R.id.progress_activity_loss_per_day);
        daysleft=findViewById(R.id.progress_activity_days_left);
        Entries=new ArrayList<>();

        sharedPreferences=getSharedPreferences(String.valueOf(R.string.preference_file_name),MODE_PRIVATE);
        dbOpenHelper=new DbOpenHelper(this);
        cursor=dbOpenHelper.Fetch_Database(Contract.DbWeight.TableName);
        dateHandler=new DateHandler();

        dailySummFrag= (DailySummFrag) getSupportFragmentManager().findFragmentById(R.id.progress_act_fragment);
        Dates=new ArrayList<>();
        Muscle=new ArrayList<>();
        BodyFat=new ArrayList<>();
        Calories=new ArrayList<>();
        Weight=new ArrayList<>();

        nod=Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
                -sharedPreferences.getInt("Start_Date",Calendar.getInstance().get(Calendar.DAY_OF_YEAR))+1;
        setFragment();
        setLineChart();
        setProgress();


    }

    public void setProgress(){
        float cgoalwt=sharedPreferences.getFloat("Goal_Weight",0);
        float cwt=sharedPreferences.getFloat("Weight",0);
        boolean flag=false;
        dateHandler=new DateHandler();
        while (!flag){
            cdate=dateHandler.getDate_store().split("/");
            cursor=dbOpenHelper.Fetch_Database(cdate[2],cdate[1],cdate[0], Contract.DbWeight.TableName);
            cursor.moveToFirst();
            if(!cursor.isAfterLast()){
                flag=true;
               cwtchange=cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight))-cwt;
               cursor.moveToNext();
            }
            dateHandler.decrementDate();
        }
        cwtperday=cwtchange/nod;
        perdayloss.setText("Weight loss per day : "+new DecimalFormat("0.000").format(Math.abs(cwtperday))+" Kg");
        daysleft.setText(String.valueOf(Math.ceil(cgoalwt/Math.abs(cwtchange)))+" Days");
    }
    public void setLineChart(){
        XAxis xAxis=lineChart.getXAxis();
        YAxis yAxisl=lineChart.getAxisLeft();
        YAxis yAxisr=lineChart.getAxisRight();

        xAxis.setValueFormatter(new XAxisFormat(nod));
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(8f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        yAxisl.setEnabled(false);
        yAxisr.setEnabled(false);
        lineChart.setVisibleXRangeMaximum(10);
        lineChart.moveViewToX(30);

        initList();

        lineDataSet=new LineDataSet(Entries,"");
        lineData=new LineData(lineDataSet);
        lineDataSet.setLineWidth(4f);
       // lineDataSet.setDrawCircles(false);
        lineData.setHighlightEnabled(false);
        lineData.setValueTextSize(12f);
        lineChart.setDescription(null);
        lineChart.setDragYEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setFocusable(true);
        lineChart.setVisibleXRangeMinimum(10f);
        lineChart.zoom(nod/7,1f,0,0);
        lineChart.setData(lineData);
        lineChart.animateXY(1000,500);

    }

    public void initList(){
        int i=0;
        while(i<Weight.size()){
            Entries.add(new Entry(i,Weight.get(Weight.size()-i-1)));
            i++;
        }
        Entries.add(new Entry(i,0));
    }

    public void setFragment(){
        dateHandler=new DateHandler();
        int i=nod;
        while(i-->0) {
            cdate = dateHandler.getDate_store().split("/");
            Dates.add(dateHandler.getDate_show());
            cursor = dbOpenHelper.Fetch_Database(cdate[2], cdate[1], cdate[0], Contract.Dbnutrition.TableName);
            float cal = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cal += cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie));
                cursor.moveToNext();
            }
            Calories.add(cal);
            cursor = dbOpenHelper.Fetch_Database(cdate[2], cdate[1], cdate[0], Contract.DbWeight.TableName);
            float wt = 0,bdf=0,msc=0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                wt = cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight));
                bdf = cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Body_Fat));
                msc = cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Body_Muscle));
                cursor.moveToNext();
            }
            Weight.add(wt);
            BodyFat.add(bdf);
            Muscle.add(msc);
            dateHandler.decrementDate();
        }
        dailySummFrag.rvAdapterDaySummary.SetData(true,Dates,Weight,Calories,BodyFat,Muscle);

    }
}

class XAxisFormat implements IAxisValueFormatter{
    public XAxisFormat(int nod) {
        this.nod = nod;
    }

    int nod;
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        DateHandler dateHandler=new DateHandler();
        if(Math.ceil(value)==Math.floor(value)) {
            dateHandler.move_date(-1 * (nod - 1 - (int) value));
            Date date = dateHandler.getDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM/dd");
            return simpleDateFormat.format(date);
        }
        return "";
    }
}


