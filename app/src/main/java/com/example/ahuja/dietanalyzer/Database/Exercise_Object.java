package com.example.ahuja.dietanalyzer.Database;

public class Exercise_Object {

    public String day;
    public String month;
    public String year;
    public String Desc;
    public float calories_burnt;

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getDesc() {
        return Desc;
    }

    public float getCalories() {
        return calories_burnt;
    }

    public Exercise_Object(String day, String month, String year, String desc, float calories) {
        this.day = day;
        this.month = month;
        this.year = year;
        Desc = desc;
        this.calories_burnt = calories;
    }
}
