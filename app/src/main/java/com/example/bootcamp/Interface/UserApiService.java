package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("login")
    Call<ApiResult> userLogin(@Body Login loginBody);
}
