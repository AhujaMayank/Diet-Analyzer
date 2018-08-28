package com.example.ahuja.dietanalyzer.Database;

public class Weight_Object {


    public String day;
    public String month;
    public String year;
    public float weight;
    public float body_fat;
    public float body_muscle;

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public float getWeight() {
        return weight;
    }

    public float getBody_fat() {
        return body_fat;
    }

    public float getBody_muscle() {
        return body_muscle;
    }

    public Weight_Object(String day, String month, String year, float weight, float body_fat, float body_muscle) {

        this.day = day;
        this.month = month;
        this.year = year;
        this.weight = weight;
        this.body_fat = body_fat;
        this.body_muscle = body_muscle;
    }
}
