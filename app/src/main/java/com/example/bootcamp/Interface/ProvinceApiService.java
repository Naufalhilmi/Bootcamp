package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ProvinceApiService {
    @GET("api/province/getAll")
    Call<ApiResult> getAllProvinces(@Header("Authorization") String token);

    @GET("api/province/getById")
    Call<ApiResult> getProvinceById(@Header("Authorization") String token, @Query("id") int id);
}
