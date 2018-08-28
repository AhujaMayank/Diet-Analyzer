package com.example.ahuja.dietanalyzer.Connect_API.Model;

import com.google.gson.annotations.SerializedName;

public class NutritionJson {

    @SerializedName("ENERC_KCAL")
    private String ENERC_KCAL;
    @SerializedName("PROCNT")
    private String PROCNT;
    @SerializedName("FAT")
    private String FAT;
    @SerializedName("CHOCDF")
    private String  CHOCDF;

    public void setENERC_KCAL(String ENERC_KCAL) {
        this.ENERC_KCAL = ENERC_KCAL;
    }

    public void setPROCNT(String PROCNT) {
        this.PROCNT = PROCNT;
    }

    public void setFAT(String FAT) {
        this.FAT = FAT;
    }

    public void setCHOCDF(String CHOCDF) {
        this.CHOCDF = CHOCDF;
    }

    public String getENERC_KCAL() {

        return ENERC_KCAL;
    }

    public String getPROCNT() {
        return PROCNT;
    }

    public String getFAT() {
        return FAT;
    }

    public String getCHOCDF() {
        return CHOCDF;
    }
}
