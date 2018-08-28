package com.example.ahuja.dietanalyzer.Connect_API.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodJson {

    @SerializedName("uri")
    private String uri;
    @SerializedName("label")
    private String label;
    @SerializedName("nutrients")
    NutritionJson nutrients;
    @SerializedName("source")
    private String source;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setNutrients(NutritionJson nutrients) {
        this.nutrients = nutrients;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUri() {

        return uri;
    }

    public String getLabel() {
        return label;
    }

    public NutritionJson getNutrients() {
        return nutrients;
    }

    public String getSource() {
        return source;
    }
}
