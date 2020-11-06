package com.example.bootcamp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bootcamp.Interface.AppService;
import com.example.bootcamp.Interface.DistrictApiService;
import com.example.bootcamp.Interface.ProvinceApiService;
import com.example.bootcamp.Interface.RegenciesApiService;
import com.example.bootcamp.Interface.VillageApiService;
import com.example.bootcamp.R;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.District;
import com.example.bootcamp.model.Province;
import com.example.bootcamp.model.Regencie;
import com.example.bootcamp.model.Village;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserAddressFragment extends Fragment {
    private String TAG = "userAddressFragment";
    private View view;
    private Retrofit retrofit;
    private Spinner spinnerProvince, spinnerRegencie, spinnerDistrict, spinnerVillage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_address, container, false);
        initView();

        return view;
    }

    private void initView() {
        retrofit = RetrofitUtility.initializeRetrofit();

        spinnerProvince = view.findViewById(R.id.spin_provinces);
        spinnerRegencie = view.findViewById(R.id.spin_regencie);
        spinnerDistrict = view.findViewById(R.id.spin_district);
        spinnerVillage =  view.findViewById(R.id.spin_villages);

        getProvinces();

    }

   private void getProvinces(){
        ProvinceApiService provinceApiService = retrofit.create(ProvinceApiService.class);
        Call<ApiResult> result = provinceApiService.getAllProvinces(AppService.getToken());

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Log.e(TAG, "onResponse: " + response.body() );
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type listType = new TypeToken<List<Province>>(){}.getType();

                List<Province> provinces = gson.fromJson(gson.toJson(apiResult.getData()), listType);
                setProvinceSpinner(provinces);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

            }
        });
   }

    private void setProvinceSpinner(List<Province> provinceList){
        List<String> provinces = new ArrayList<>();
        provinces.add("Choose Province");

        for (Province province : provinceList){
            provinces.add(province.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, provinces){
            @Override
            public boolean isEnabled(int position){
                if (position == 0){
                    return false;
                } else {
                    return true;
                }
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(dataAdapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position);
                String selectedId = position == 0 ? null : String.valueOf(position -1);

                if (selectedId == null) {
                    Log.e(TAG, "onItemSelected: " + null);
                } else {
                    Log.e(TAG, "pilihan selain itu ");
                    Log.e(TAG, "onItemSelected: " + provinceList.get(Integer.parseInt(selectedId)).getId());

                    Integer provinceId = provinceList.get(Integer.parseInt(selectedId)).getId();

                    getRegencies(provinceId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e(TAG, "onNothingSelected: " + parent);
            }

        });

    }

    public void getRegencies(int provinceId){
        RegenciesApiService regenciesApiService = retrofit.create(RegenciesApiService.class);
        Call<ApiResult> result = regenciesApiService.getRegencieByProvinceId(AppService.getToken(), provinceId);

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type listType = new TypeToken<List<Regencie>>(){}.getType();
                List<Regencie> regencies = gson.fromJson(gson.toJson(apiResult.getData()), listType);
                setRegencieSpinner(regencies);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t );
            }
        });
    }

    private void setRegencieSpinner(List<Regencie> regencieList){
        List<String> regencies = new ArrayList<>();
        regencies.add("Choose Regencie");

        for (Regencie regencie : regencieList){
            regencies.add(regencie.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, regencies){
            @Override
            public boolean isEnabled(int position){
                if (position == 0){
                    return false;
                }else {
                    return true;
                }
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegencie.setAdapter(dataAdapter);
        spinnerRegencie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: " + position );
                String selectedId = position == 0 ? null : String.valueOf(position - 1);

                if (selectedId == null){
                    Log.e(TAG, "onItemClick: " + null );
                }else {
                    Log.e(TAG, "pilihan selain itu ");

                    int regencyId = regencieList.get(Integer.parseInt(selectedId)).getId();

                    Log.e(TAG, "onItemSelected: " + regencyId );
                    getDistrict(regencyId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                Log.e(TAG, "onNothingSelected: "+ parent);
            }
        });
    }

    private void getDistrict (int regencyId){
        DistrictApiService districtApiService = retrofit.create(DistrictApiService.class);
        Call<ApiResult> result = districtApiService.getAllRegencyId(AppService.getToken(), regencyId);

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type lisType = new TypeToken<List<District>>(){}.getType();
                List<District> districts = gson.fromJson(gson.toJson(apiResult.getData()), lisType);
                setDistrictSpinner(districts);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t );
            }
        });
    }

    private void setDistrictSpinner(List<District> districtList){
        List<String> districts = new ArrayList<>();

        for (District district : districtList){
            districts.add(district.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, districts){
            @Override
            public boolean isEnabled(int position){
                if (position == 0) {
                    return false;
                }else {
                    return true;
                }
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(dataAdapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position );
                String selectedId = position == 0 ? null : String.valueOf(position - 1);

                if (selectedId == null){
                    Log.e(TAG, "onItemSelected: " + null );
                }else {
                    Log.e(TAG, "pilihan selain itu ");
                    int districtId = districtList.get(Integer.parseInt(selectedId)).getId();
                    Log.e(TAG, "onItemSelected: " + districtId );
                    getVillages(districtId);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                Log.e(TAG, "onNothingSelected: " + parent );
            }
        });
    }

    private void getVillages(Integer districtId){
        VillageApiService villageApiService = retrofit.create(VillageApiService.class);
        Call<ApiResult> result = villageApiService.getAllVillageId(AppService.getToken(), districtId);

        result.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                Gson gson = new Gson();
                ApiResult apiResult = response.body();

                Type listType = new TypeToken<List<Village>>(){}.getType();
                List<Village> villages = gson.fromJson(gson.toJson(apiResult.getData()), listType);
                Log.e(TAG, "onResponse: " + villages );
                setVillageSpinner(villages);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t );
            }
        });
    }

    private void setVillageSpinner(List<Village> villagesList) {
        List<String> villages = new ArrayList<>();
        villages.add("Choose Village");

        for (Village village : villagesList) {
            villages.add(village.getName());
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, villages) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVillage.setAdapter(dataAdapter);
        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position);
                String selectedId = position == 0 ? null : String.valueOf(position - 1);

                if (selectedId == null) {
                    Log.e(TAG, "onItemSelected: " + null);
                } else {
                    Log.e(TAG, "pilihan selain itu ");
                    Integer districtId = villagesList.get(Integer.parseInt(selectedId)).getId();
                    Log.e(TAG, "onItemSelected: " + districtId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e(TAG, "onNothingSelected: " + parent);
            }
        });

    }
}