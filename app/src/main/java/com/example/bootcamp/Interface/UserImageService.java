package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.UserImage;

import kotlin.reflect.KCallable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserImageService {

    @GET("avatar/getByid")
    Call<ApiResult> getUserImage(@Header("Authorization") String token, @Query("user_id") Long user_id);

    @POST("avatar/insert")
    Call<ApiResult> insertUserImage(@Header("Authorization") String token, @Body UserImage userImage);

    @PUT("avatar/update")
    Call<ApiResult> updateUserImage(@Header("Authorization") String token, @Body UserImage userImage);
}
