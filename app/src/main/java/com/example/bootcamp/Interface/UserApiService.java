package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.Login;
import com.example.bootcamp.model.Register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApiService {

    @POST("login")
    Call<ApiResult> userLogin(@Body Login loginBody);

    @POST("register")
    Call<ApiResult> userRegister(@Body Register registerBody);


}
