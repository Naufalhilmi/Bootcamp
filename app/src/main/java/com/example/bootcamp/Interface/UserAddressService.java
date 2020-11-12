package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.UserAddress;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAddressService {
    @POST("adress/insert")
    Call<ApiResult> insertUserAddress(@Header("Authorization") String token,@Body UserAddress userAddress);

    @GET("adress/getByid")
    Call<ApiResult> getUserAddressById(@Header("Authorization") String token, @Query("userid") String userid);
}
