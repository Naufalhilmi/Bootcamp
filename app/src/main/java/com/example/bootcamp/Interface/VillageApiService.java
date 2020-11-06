package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface VillageApiService {
    @GET("api/villages/getByDistrictId")
    Call<ApiResult> getAllVillageId(@Header("Authorization") String token, @Query("districtId") Integer districtId);
}
