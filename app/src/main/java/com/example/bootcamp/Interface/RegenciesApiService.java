 package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RegenciesApiService {
    @GET("api/regencies/getByProvinceId")
    Call<ApiResult> getRegencieByProvinceId(@Header("Authorization") String token, @Query("provinceId") Integer provinceId);

    @GET("api/regencies/getById")
    Call<ApiResult> getRegencieById(@Header("Authorization") String token, @Query("id") int id);
}
