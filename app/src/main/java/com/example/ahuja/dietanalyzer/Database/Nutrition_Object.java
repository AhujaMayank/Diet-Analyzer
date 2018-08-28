package com.example.ahuja.dietanalyzer.Database;

public class Nutrition_Object {

    public String day;
    public String month;
    public String year;
    public String meal_type;
    public String Desc;
    public float calories;
    public float carbs;
    public float fats;
    public float proteins;
    public float sodium;


    public Nutrition_Object(String day, String month, String year, String meal_type, String desc, float calories, float carbs, float fats, float proteins, float sodium) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.meal_type = meal_type;
        Desc = desc;
        this.calories = calories;
        this.carbs = carbs;
        this.fats = fats;
        this.proteins = proteins;
        this.sodium = sodium;

    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public String getDesc() {
        return Desc;
    }

    public float getCalories() {
        return calories;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getFats() {
        return fats;
    }

    public float getProteins() {
        return proteins;
    }

    public float getSodium() {
        return sodium;
    }
}
