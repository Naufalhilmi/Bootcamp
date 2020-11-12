package com.example.bootcamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.bootcamp.Adapter.MovieAdapter;
import com.example.bootcamp.Interface.AppService;
import com.example.bootcamp.Interface.MovieApiService;
import com.example.bootcamp.fragment.RetrofitUtility;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.Movie;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> moviesArrayList = new ArrayList<>();
    private boolean doubleBackToExitPressedOnce;
    private String TAG = "MainActivity";
    private MaterialToolbar topAppBar;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMovieData();

        ImageButton setting = (ImageButton) findViewById(R.id.btn_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });
    }

    private void openUserSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }


    private void getMovieData() {
        retrofit = RetrofitUtility.initializeRetrofit();
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<ApiResult> resultCall = movieApiService.getAllMovie(AppService.getToken());

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Movie>>() {
                }.getType();

                List<Movie> movies = gson.fromJson(gson.toJson(result.getData()), listType);
                moviesArrayList.addAll(movies);

                setRecyclerView();
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);

            }
        });
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_main);
        movieAdapter = new MovieAdapter(moviesArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieAdapter);

    }

    private void toMovieDetailActivity(){
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        startActivity(intent);
        finish();
    }
}