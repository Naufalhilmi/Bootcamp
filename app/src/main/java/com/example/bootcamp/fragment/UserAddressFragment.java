package com.example.bootcamp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bootcamp.Interface.AppService;
import com.example.bootcamp.Interface.DistrictApiService;
import com.example.bootcamp.Interface.ProvinceApiService;
import com.example.bootcamp.Interface.RegenciesApiService;
import com.example.bootcamp.Interface.UserAddressService;
import com.example.bootcamp.Interface.VillageApiService;
import com.example.bootcamp.R;
import com.example.bootcamp.model.ApiResult;
import com.example.bootcamp.model.District;
import com.example.bootcamp.model.Province;
import com.example.bootcamp.model.Regencie;
import com.example.bootcamp.model.UserAddress;
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
    private String user_id;
    private int province_id;
    private int regencie_id;
    private int district_id;
    private int village_id;
    private String address, pos_code;
    private Spinner spinnerProvince, spinnerRegencie, spinnerDistrict, spinnerVillage;
    private EditText editAddress, editPostCode;
    private Button buttonSave;
    int provinceId, regencyId, districtId, villageId;

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
        editAddress = view.findViewById(R.id.insert_address);
        editPostCode = view.findViewById(R.id.insert_kodepos);
        buttonSave =  view.findViewById(R.id.saveBtn);
        getProvinces();


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: button save ditekan");
                user_id = AppService.getUser().getId().toString();
                address = editAddress.getText().toString();
                pos_code = editPostCode.getText().toString();
                province_id = provinceId;
                regencie_id = regencyId;
                district_id = districtId;
                village_id = villageId;
                UserAddress userAddress = new UserAddress(user_id, address, province_id, regencie_id, district_id, village_id, pos_code);
                if (address.equals("")){
                    Toast.makeText(getContext(), "Input Address Kosong", Toast.LENGTH_SHORT).show();
                }else if (pos_code.equals("")){
                    Toast.makeText(getContext(), "Input KodePos Kosong", Toast.LENGTH_SHORT).show();
//                }else if (province_id == null){
//                    Toast.makeText(getContext(), "Input Province Kosong", Toast.LENGTH_SHORT).show();
//                }else if (regencie_id == null){
//                    Toast.makeText(getContext(), "Input Regencie Kosong", Toast.LENGTH_SHORT).show();
//                }else if (district_id == null){
//                    Toast.makeText(getContext(), "Input District Kosong", Toast.LENGTH_SHORT).show();
//                }else if (village_id == null){
//                    Toast.makeText(getContext(), "Input village Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    sendAddress(userAddress);
                }

            }
        });

    }

    private void sendAddress(UserAddress userAddress){
        UserAddressService userAddressService = retrofit.create(UserAddressService.class);
        Call<ApiResult> result = userAddressService.insertUserAddress(AppService.getToken(), userAddress);

        result.enqueue(new Callback<ApiResult>() {

            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResponse = response.body();
                boolean success = apiResponse.isSuccess();
                if (success) {
                    Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

                    provinceId = provinceList.get(Integer.parseInt(selectedId)).getId();

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

                    regencyId = regencieList.get(Integer.parseInt(selectedId)).getId();

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
        districts.add("Choose District");

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
                    districtId = districtList.get(Integer.parseInt(selectedId)).getId();
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
                    villageId = villagesList.get(Integer.parseInt(selectedId)).getId();
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