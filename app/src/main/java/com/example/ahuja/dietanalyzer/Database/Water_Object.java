package com.example.ahuja.dietanalyzer.Database;

public class Water_Object {

    public String day,month,year;
    public float litre,millilitres,glasses;

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public float getLitre() {
        return litre;
    }

    public float getMillilitres() {
        return millilitres;
    }

    public float getGlasses() {
        return glasses;
    }

    public Water_Object(String day, String month, String year, float litre, float millilitres, float glasses) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.litre = litre;
        this.millilitres = millilitres;
        this.glasses = glasses;
    }
}
