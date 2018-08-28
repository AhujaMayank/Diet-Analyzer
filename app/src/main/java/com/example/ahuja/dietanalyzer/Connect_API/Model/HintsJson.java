package com.example.ahuja.dietanalyzer.Connect_API.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HintsJson {
    public void setFood(FoodJson food) {
        this.food = food;
    }

    public void setMeasures(List<MeasureJson> measures) {
        this.measures = measures;
    }

    public FoodJson getFood() {

        return food;
    }

    public List<MeasureJson> getMeasures() {
        return measures;
    }

    @SerializedName("food")
    private FoodJson food;

    @SerializedName("measures")
    List<MeasureJson> measures;
}
