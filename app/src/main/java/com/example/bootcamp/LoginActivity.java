package com.example.bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bootcamp.Interface.AppService;
import com.example.bootcamp.Interface.UserApiService;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.Login;
import com.example.bootcamp.model.User;
import com.example.bootcamp.utilities.Cons;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";
    private String userName, password;
    private EditText inputUsername, inputPassword;
    private Button btnLogin, btnRegister;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initRetrofit();

        TextView register = (TextView)findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity2.class);
                startActivity(i);
            }
        });

    }

    private void initView() {
        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: button login ditekan ");
                userName = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                Login login = new Login(userName, password);
                if (userName.equals("")){
                    Toast.makeText(LoginActivity.this, "Input Username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals("")){
                    Toast.makeText(LoginActivity.this, "Input Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    sendLogin(login);
                }
                Log.i(TAG, "isi username : " + userName);
                Log.i(TAG, "isi password : " + password);
            }
        });
    }

    private void initRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(); //intercept semua log http
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Cons.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    private void sendLogin(Login loginBody) {
        UserApiService userApiService = retrofit.create(UserApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = userApiService.userLogin(loginBody);   // call method interfacenya

        result.enqueue(new  Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResponse = response.body();
                boolean success = apiResponse.isSuccess();
                Log.i(TAG, "onResponse: " +success);
                if (success) {
                    Gson gson = new Gson();
                    Toast.makeText(LoginActivity.this, "Selamat Datang", Toast.LENGTH_SHORT).show();
                    User userResult = gson.fromJson(gson.toJson(apiResponse.getData()), User.class);
                    AppService.setToken("Bearer "+apiResponse.getToken());
                    AppService.setUser(userResult);
                    toMainActivity();
                } else {
                    Toast.makeText(LoginActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toMainActivity(){
        Log.i(TAG, "toMainActivity: ");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}