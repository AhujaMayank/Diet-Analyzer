package com.example.ahuja.dietanalyzer.Connect_API.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParsedJson {
    @SerializedName("food")
    private List<FoodJson> food;

    public void setFood(List<FoodJson> food) {
        this.food = food;
    }

    public List<FoodJson> getFood() {

        return food;
    }
}
