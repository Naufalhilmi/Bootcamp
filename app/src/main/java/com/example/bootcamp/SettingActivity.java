package com.example.bootcamp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bootcamp.Interface.AppService;
import com.example.bootcamp.Interface.UserAddressService;
import com.example.bootcamp.Interface.UserApiService;
import com.example.bootcamp.Interface.UserImageService;
import com.example.bootcamp.fragment.RetrofitUtility;
import com.example.bootcamp.fragment.UserAddressDataFragment;
import com.example.bootcamp.fragment.UserAddressFragment;
import com.example.bootcamp.fragment.UserImageFragment;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.Login;
import com.example.bootcamp.model.UserAddress;
import com.example.bootcamp.model.UserImage;
import com.example.bootcamp.utilities.Cons;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity {
    private String TAG = "SettingActivity";
    private Retrofit retrofit;
    private static final int PICK_IMAGE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private String base64Image;
    ImageView mImageView;
    Button mChooseBtn, buttonSave, buttonUpdate;
    private Long user_id;
    private String avatar;
    TabLayout tabLayout;
    private UserAddress userAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        TabLayout tabLayout = findViewById(R.id.tabLayout);
//        TabItem tabImage = findViewById(R.id.tabImage);
//        TabItem tabAddress = findViewById(R.id.tabAddress);
//        ViewPager viewPager = findViewById(R.id.viewPager);
//
//        PagerAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        getUserAvatar();
        openUserImageFragment();
    }

    private void getUserAvatar() {
        Long user_id = AppService.getUser().getId();
        tabLayout = findViewById(R.id.tabLayout);

        retrofit = RetrofitUtility.initializeRetrofit();

        UserImageService userImageService = retrofit.create(UserImageService.class);  //instansiasi interfacenya ke retrofit
        Call<ApiResult> result = userImageService.getUserImage(AppService.getToken(), user_id);   // call method interfacenya


        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Log.e(TAG, "User setting response : " + response);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure user setting: " + t);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.e(TAG, "onTabSelected: " + position);
                if (position == 0) {
                    Log.e(TAG, "onTabSelected: user image");
                    openUserImageFragment();
                } else {
                    Log.e(TAG, "onTabSelected: user address");
//                    openUserAddressFragment();
                    getUserAddressData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabUnselected: " + tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabReselected: " + tab);
            }
        });

    }

    public void openUserImageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserImageFragment strCode = new UserImageFragment();
        fragmentTransaction.replace(R.id.content, strCode, "UserImageFragment");
        fragmentTransaction.commit();
    }

    private void getUserAddressData() {

        String user_id = String.valueOf(AppService.getUser().getId());

        UserAddressService userAddressApiService = retrofit.create(UserAddressService.class);
        Call<ApiResult> resultCall = userAddressApiService.getUserAddressById(AppService.getToken(), user_id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                if (result.isSuccess()) {
                    Gson gson = new Gson();
                    userAddress = gson.fromJson(gson.toJson(result.getData()), UserAddress.class);
                    openUserAddressDataFragment();
                } else {
                    Log.e(TAG, "onResponse failed : ");
                    openUserAddressFragment();
                }

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
                Toast.makeText(SettingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void openUserAddressFragment() {
        Log.e(TAG, "openUserAddressFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserAddressFragment strCode = new UserAddressFragment();
        fragmentTransaction.replace(R.id.content, strCode, "userAddressFragment");
        fragmentTransaction.commit();
    }

    public void openUserAddressDataFragment() {
        Log.e(TAG, "openUserAddressDataFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserAddressDataFragment strCode = new UserAddressDataFragment();
        fragmentTransaction.replace(R.id.content, strCode, "userAddressF");
        fragmentTransaction.commit();
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
//        mImageView = findViewById(R.id.image_view);
//        mChooseBtn = findViewById(R.id.btn_Cimage);
//        buttonSave = findViewById(R.id.btn_save);
//        buttonUpdate = findViewById(R.id.btn_update);
//        initRetrofit();
//        getImage();
//
//        mChooseBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick: Button save ditekan");
//                UserImage userImage = new UserImage(user_id, avatar);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                            ==  PackageManager.PERMISSION_DENIED){
//                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                        requestPermissions(permissions, PERMISSION_CODE);
//                    }
//                    else {
//                        pickImageFromGallery();
//                    }
//                }
//                else {
//                    pickImageFromGallery();
//                }
//            }
//        });
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                user_id = AppService.getUser().getId();
//                avatar = base64Image;
//                UserImage userImage = new UserImage(user_id, base64Image);
//                sendImage(userImage);
//                Log.e(TAG, "value id : " +user_id);
//
//            }
//        });
//
//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserImage userImage = new UserImage();
//                updateImage();
//            }
//        });
//    }
//
//    private void pickImageFromGallery() {
//        Intent intent =  new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_IMAGE);
//    }
//
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case PERMISSION_CODE: {
//                if (grantResults.length > 0 && grantResults[0]
//                        == PackageManager.PERMISSION_GRANTED){
//                    pickImageFromGallery();
//                }
//                else {
//                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
//            mImageView.setImageURI(data.getData());
//            Uri uri = data.getData();
//            InputStream imageStream;
//            String encodeImage = "";
//            try {
//                imageStream = getApplicationContext().getContentResolver().openInputStream(uri);
//                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                encodeImage = encodeImage + encodeImage(selectedImage);
//        }catch (FileNotFoundException d){
//                d.printStackTrace();
//            }
//            base64Image = encodeImage;
//    }
//}
//
//    private void sendImage(UserImage userImageBody){
//        UserImageService userImageService = retrofit.create(UserImageService.class);
//        Call<ApiResult> result = userImageService.insertUserImage(AppService.getToken(), userImageBody);
//
//        result.enqueue(new Callback<ApiResult>() {
//            @Override
//            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
//                ApiResult apiResponse = response.body();
//                boolean success = apiResponse.isSuccess();
//                Log.i(TAG, "onResponse: " +success);
//                if (success) {
//                    Toast.makeText(SettingActivity.this, "Berhasil Upload image", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(SettingActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResult> call, Throwable t) {
//                Toast.makeText(SettingActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void getImage (){
//        Long user_id = AppService.getUser().getId();
//        UserImageService userImageService = retrofit.create(UserImageService.class);
//        Call<ApiResult> result = userImageService.getUserImage(AppService.getToken(), user_id);
//
//        result.enqueue(new Callback<ApiResult>() {
//            @Override
//            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
//                Gson gson = new Gson();
//                ApiResult apiResponse = response.body();
//                boolean success = apiResponse.isSuccess();
//                if (success) {
//                    UserImage userImageResult = gson.fromJson(gson.toJson(apiResponse.getData()), UserImage.class);
//                    SetImageThumb(userImageResult.getAvatar());
//                    setButtonVisibility(View.GONE, View.VISIBLE);
//                   // Toast.makeText(SettingActivity.this, "Berhasil Upload image", Toast.LENGTH_SHORT).show();
//                } else {
//                    setButtonVisibility(View.VISIBLE, View.GONE);
//                    //Toast.makeText(SettingActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResult> call, Throwable t) {
//                Toast.makeText(SettingActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//
//    public void updateImage(){
//        UserImage userImage = new UserImage();
//        userImage.setUser_id(AppService.getUser().getId());
//        userImage.setAvatar(base64Image);
//
//        UserImageService userImageService = retrofit.create(UserImageService.class);
//        Call<ApiResult> resultCall = userImageService.updateUserImage(AppService.getToken(), userImage);
//
//        resultCall.enqueue(new Callback<ApiResult>() {
//            @Override
//            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
//                ApiResult result = response.body();
//                Log.e(TAG, "onResponse: " + result );
//                if (result.isSuccess()){
//                    Toast.makeText(SettingActivity.this, "Berhasil Update image", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(SettingActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResult> call, Throwable t) {
//                Toast.makeText(SettingActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    private String encodeImage(Bitmap bm) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
//        byte[] b = byteArrayOutputStream.toByteArray();
//
//        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
//        return encImage;
//    }
//
//    private void SetImageThumb(String base64String){
//        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
//        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
//        mImageView.setImageBitmap(decodeByte);
//    }
//
//    private void setButtonVisibility(int saveState, int updateState) {
//        buttonSave.setVisibility(saveState);
//        buttonUpdate.setVisibility(updateState);
//    }
//
//    private void initRetrofit() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(); //intercept semua log http
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient
//                .Builder()
//                .addInterceptor(interceptor)
//                .build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(Cons.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//}