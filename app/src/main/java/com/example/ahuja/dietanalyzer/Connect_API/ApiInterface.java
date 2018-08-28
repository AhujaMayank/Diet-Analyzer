package com.example.ahuja.dietanalyzer.Connect_API;

import com.example.ahuja.dietanalyzer.Connect_API.Model.MainJson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("parser")
    Call<MainJson> getQueryResult(@Query("app_id")String app_id,@Query("app_key") String app_key,
                                  @Query("ingr") String ingr);
    @GET()
    Call<MainJson> getNextResult(@Query("app_id")String app_id,@Query("app_key") String app_key,
                                  @Query("ingr") String ingr,@Query("page")int page);

}
