package com.example.ahuja.dietanalyzer.Connect_API;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.ahuja.dietanalyzer.Connect_API.Model.HintsJson;
import com.example.ahuja.dietanalyzer.Connect_API.Model.MainJson;
import com.example.ahuja.dietanalyzer.Connect_API.Model.ParsedJson;
import com.example.ahuja.dietanalyzer.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryHandler {

    //https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
    public QueryHandler() {
    }
    private final String parser_base_url = "https://api.edamam.com/api/food-database/";
    private final String api_key = "66ea27f2315504b7236c297ed3996c59";
    private final String api_id = "ff9e647f";
    private final String nutrition_base_url = "https://api.edamam.com/api/food-database/nutrients";

    public String getParser_base_url() {
        return parser_base_url;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getApi_id() {
        return api_id;
    }

    public String getNutrition_base_url() {
        return nutrition_base_url;
    }

    public Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(parser_base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
