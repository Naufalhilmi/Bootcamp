package com.example.bootcamp.Interface;

import com.example.bootcamp.model.ApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("api/movie/getAll")
    Call<ApiResult> getAllMovie(@Header("Authorization") String token);

    @GET("api/movie/getById")
    Call<ApiResult> getMovieById(@Header("Authorization") String token, @Query("id") long id);

}
