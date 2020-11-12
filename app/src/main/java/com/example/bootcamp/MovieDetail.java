package com.example.bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bootcamp.Interface.AppService;
import com.example.bootcamp.Interface.MovieApiService;
import com.example.bootcamp.fragment.RetrofitUtility;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.Movie;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetail extends AppCompatActivity {

    private ImageView imageMovie;
    private TextView txtJudul, txtRating, txtSinopsis, txtSutradara, txtPemain, labelSutradara, labelPemain, labelSinopsis;
    private Long movie_Id;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        movie_Id = intent.getLongExtra("movieId", 0);
        initView();
        getMovieById(movie_Id);
    }

    private void initView(){
        txtJudul = findViewById(R.id.txt_judulDet);
        txtRating = findViewById(R.id.txt_ratingDet);
        txtSinopsis = findViewById(R.id.txt_sinopsisDet);
        txtSutradara = findViewById(R.id.txt_sutradaraDet);
        txtPemain = findViewById(R.id.txt_pemainDet);
        imageMovie = findViewById(R.id.image_posterDetail);
        labelSutradara = findViewById(R.id.labelSut);
        labelPemain = findViewById(R.id.labelPemain);
        labelSinopsis = findViewById(R.id.lblSinopsis);

    }

    public void getMovieById(long movie_Id){
        retrofit = RetrofitUtility.initializeRetrofit();
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<ApiResult> resultCall = movieApiService.getMovieById(AppService.getToken(), movie_Id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResult = response.body();
                Gson gson = new Gson();
                Movie movie = gson.fromJson(gson.toJson(apiResult.getData()), Movie.class);
                setMovieDetail(movie);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

            }
        });
    }

    private void setMovieDetail(Movie movieBody){
        txtJudul.setText(movieBody.getJudul());
        txtRating.setText(movieBody.getRating());
        txtSutradara.setText(movieBody.getSutradara());
        txtPemain.setText(movieBody.getCast());
        txtSinopsis.setText(movieBody.getSinopsis());
        labelSutradara.setText("Sutradara :");
        labelPemain.setText("Pemain :");
        labelSinopsis.setText("Sinopsis");
        imageMovie.setImageBitmap(convertToBitmap(movieBody.getImage()));

    }

    private Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}