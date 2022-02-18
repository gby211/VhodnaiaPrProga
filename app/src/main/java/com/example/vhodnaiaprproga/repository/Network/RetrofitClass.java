package com.example.vhodnaiaprproga.repository.Network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass {
    private APIMoney api;

    public RetrofitClass() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/488763b9c16219a38f41cf4f/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(APIMoney.class);
    }

    public LiveData<ConvertData> getValue(String convertFrom, String convertTo){

        MutableLiveData<ConvertData> stockPrice = new MutableLiveData<>();

        Call<ConvertData> call = api.getConvertDataFromAPI(convertFrom, convertTo, BuildConfig.API_KEY);
        call.enqueue(new Callback<ConvertData>() {
            @Override
            public void onResponse(Call<ConvertData> call, Response<ConvertData> response) {
                if (response.isSuccessful() && response.body() != null){
                    stockPrice.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ConvertData> call, Throwable t) {
                Log.e("API", "server is not responding"+t+call);
            }
        });
        return stockPrice;
    }

}