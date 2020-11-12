package com.example.bootcamp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootcamp.Interface.AppService;
import com.example.bootcamp.Interface.DistrictApiService;
import com.example.bootcamp.Interface.ProvinceApiService;
import com.example.bootcamp.Interface.RegenciesApiService;
import com.example.bootcamp.Interface.UserAddressService;
import com.example.bootcamp.Interface.VillageApiService;
import com.example.bootcamp.R;
import com.example.bootcamp.SettingActivity;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.District;
import com.example.bootcamp.model.Province;
import com.example.bootcamp.model.Regencie;
import com.example.bootcamp.model.UserAddress;
import com.example.bootcamp.model.Village;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserAddressDataFragment extends Fragment {
    private View view;
    private TextInputEditText inputAddress, inputPosCode, inputProvince, inputRegencie, inputDistrict, inputVillage;
    private Retrofit retrofit;
    private String TAG = "UserAddressData";

    private UserAddress userAddress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_address_data, container, false);
        retrofit = RetrofitUtility.initializeRetrofit();

        initView();


        return view;
    }

    private void initView() {

        inputAddress = view.findViewById(R.id.input_address);
        inputPosCode = view.findViewById(R.id.input_kodepos);
        inputProvince = view.findViewById(R.id.input_provinces);
        inputRegencie = view.findViewById(R.id.input_regencie);
        inputDistrict = view.findViewById(R.id.input_district);
        inputVillage = view.findViewById(R.id.input_villages);


        getUserAddressData();
        getData();

    }

    private void getUserAddressData() {
        String userId = String.valueOf(AppService.getUser().getId());


        UserAddressService userAddressService = retrofit.create(UserAddressService.class);
        Call<ApiResult> resultCall = userAddressService.getUserAddressById(AppService.getToken(), userId);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();


                Log.e(TAG, "onResponse: " + result.getData());

                if (result.isSuccess()) {
                    Log.e(TAG, "onResponse ada");


                } else {
                    Log.e(TAG, "onResponse: tidak ada");
                }

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });

    }


    private void getData() {
        SettingActivity activity = (SettingActivity) getActivity();
        UserAddress userAddress = activity.getUserAddress();

        inputAddress.setText(userAddress.getAddress());
        inputPosCode.setText(userAddress.getPos_code());

        inputAddress.setEnabled(false);
        inputPosCode.setEnabled(false);
        inputProvince.setEnabled(false);
        inputRegencie.setEnabled(false);
        inputDistrict.setEnabled(false);
        inputVillage.setEnabled(false);

        inputProvince = view.findViewById(R.id.input_provinces);
        inputRegencie = view.findViewById(R.id.input_regencie);
        inputDistrict = view.findViewById(R.id.input_district);
        inputVillage = view.findViewById(R.id.input_villages);

        getProvince(userAddress.getProvince_id());
        getRegencie(userAddress.getRegencie_id());
        getDistrict(userAddress.getDistrict_id());
        getVillage(userAddress.getVillage_id());


    }

    private void getProvince(int id) {
        ProvinceApiService provincesApiService = retrofit.create(ProvinceApiService.class);
        Call<ApiResult> resultCall = provincesApiService.getProvinceById(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();
                Log.e(TAG, "onResponse: " + result );

                Gson gson = new Gson();
                Province province = gson.fromJson(gson.toJson(result.getData()), Province.class);
                inputProvince.setText(province.getName());
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }


    private void getRegencie(int id) {
        RegenciesApiService regenciesApiService = retrofit.create(RegenciesApiService.class);
        Call<ApiResult> resultCall = regenciesApiService.getRegencieById(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                Gson gson = new Gson();
                Regencie regencie = gson.fromJson(gson.toJson(result.getData()), Regencie.class);
                inputRegencie.setText(regencie.getName());
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    private void getDistrict(int id) {
        DistrictApiService districtApiService = retrofit.create(DistrictApiService.class);
        Call<ApiResult> resultCall = districtApiService.getDistrictById(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();

                Gson gson = new Gson();
                District district = gson.fromJson(gson.toJson(result.getData()), District.class);
                inputDistrict.setText(district.getName());
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    private void getVillage(int id) {
        VillageApiService villageApiService = retrofit.create(VillageApiService.class);
        Call<ApiResult> resultCall = villageApiService.getVillageById(AppService.getToken(), id);

        resultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult result = response.body();
                Log.e(TAG, "onResponse: " +result);
                Gson gson = new Gson();
                Village village = gson.fromJson(gson.toJson(result.getData()), Village.class);
//                inputVillage.setText(village.getName());
//                Log.e(TAG, "onResponse: " + village.getName() );

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Toasty.error(getBaseContext(), t.getMessage(), Toasty.LENGTH_LONG).show();


            }
        });
    }

}