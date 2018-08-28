package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import java.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler {
    java.util.Calendar calendar;
    SimpleDateFormat show,store;
    Date date;
    String date_show,date_store;

    public DateHandler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar= java.util.Calendar.getInstance();
            date=calendar.getTime();
        }
        show=new SimpleDateFormat("MMMM-dd,YYYY");
        store=new SimpleDateFormat("yyyy/MM/dd");
        date_show=show.format(date);
        date_store=store.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
        date_show=show.format(date);
        date_store=store.format(date);
    }

    public String getDate_show() {
        return date_show;
    }

    public String getDate_store() {
        return date_store;
    }
    public void move_date(int no_of_days){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar= java.util.Calendar.getInstance();
            calendar.add(java.util.Calendar.DAY_OF_YEAR,no_of_days);
            date=calendar.getTime();
        }
        date_show=show.format(date);
        date_store=store.format(date);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDateToWeekStart(int back){
        int i=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //calendar=Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR,-7*back);
            i=calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DAY_OF_YEAR,(Calendar.SUNDAY-i));

        }
        setDate(calendar.getTime());


    }

    public Date getDate() {
        return date;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void incrementDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar.add(Calendar.DAY_OF_YEAR,1);
            setDate(calendar.getTime());
        }
    }
    public void decrementDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar.add(Calendar.DAY_OF_YEAR,-1);
            setDate(calendar.getTime());
        }
    }
}



