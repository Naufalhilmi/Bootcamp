package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DistrictApiService {
    @GET("api/districts/getByRegencyId")
    Call<ApiResult> getAllRegencyId(@Header("Authorization") String token, @Query("regencyId") Integer regencyId);

    @GET("api/districts/getById")
    Call<ApiResult> getDistrictById(@Header("Authorization") String token, @Query("id") int id);
}
