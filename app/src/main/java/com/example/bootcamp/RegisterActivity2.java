package com.example.bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bootcamp.Interface.UserApiService;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.Login;
import com.example.bootcamp.model.Register;
import com.example.bootcamp.utilities.Cons;

import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity2 extends AppCompatActivity {

    private String TAG = "RegisterActivity";
    private String userName, firstName, lastName, email, password, retypePasswor;
    private EditText inputUsername, inputFirstName, inputLastName, inputEmail, inputPassword, inputRetypePassword;
    private Button buttonRegister;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initView();
        initRetrofit();
    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    private void initView(){
        inputUsername = findViewById(R.id.input_usernameRegis);
        inputFirstName = findViewById(R.id.input_firstname);
        inputLastName = findViewById(R.id.input_lastname);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_passwordRegis);
        inputRetypePassword = findViewById(R.id.input_retype_password);
        buttonRegister = findViewById(R.id.btn_regis);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: button register ditekan");
                userName = inputUsername.getText().toString();
                firstName = inputFirstName.getText().toString();
                lastName = inputLastName.getText().toString();
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                retypePasswor = inputRetypePassword.getText().toString();
                Register register = new Register(userName, firstName, lastName, email, password, retypePasswor);
                if (userName.equals("")){
                    Toast.makeText(RegisterActivity2.this, "Input username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (userName.trim().length() < 4){
                    Toast.makeText(RegisterActivity2.this, "Input Username Minimal 4 Karakter", Toast.LENGTH_SHORT).show();
                }
                else if (firstName.equals("")){
                    Toast.makeText(RegisterActivity2.this, "Input Firstname Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (lastName.equals("")){
                    Toast.makeText(RegisterActivity2.this, "Input Lastname Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (email.equals("")){
                    Toast.makeText(RegisterActivity2.this, "Input Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                    Toast.makeText(RegisterActivity2.this, "Input Email Tidak valid", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals("")){
                    Toast.makeText(RegisterActivity2.this, "Input Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (password.trim().length() < 6){
                    Toast.makeText(RegisterActivity2.this, "Input Password Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
                }
                else if (retypePasswor.equals("")){
                    Toast.makeText(RegisterActivity2.this, "Input RetypePassword Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (!retypePasswor.equals(password)){
                    Toast.makeText(RegisterActivity2.this, "Password Tidak Matching", Toast.LENGTH_SHORT).show();
                }else {
                    sendRegister(register);
                }
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

    private void sendRegister(Register registerBody) {
        UserApiService userApiService = retrofit.create(UserApiService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = userApiService.userRegister(registerBody);   // call method interfacenya

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResponse = response.body();
                boolean success = apiResponse.isSucess();
                Log.i(TAG, "onResponse: " +success);
                if (success) {
                    Toast.makeText(RegisterActivity2.this, "", Toast.LENGTH_SHORT).show();
                    toMainActivity();
                } else {
                    Toast.makeText(RegisterActivity2.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(RegisterActivity2.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toMainActivity(){
        Log.i(TAG, "toMainActivity: ");
        Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}