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

    public LiveData<Float> getValue(String currency){
        MutableLiveData<Float> value = new MutableLiveData<>();
        api.getMultiplier(currency).enqueue(new Callback<ValuePojo>() {
            @Override
            public void onResponse(Call<ValuePojo> call, Response<ValuePojo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ValuePojo valuePojo = response.body();
                    Log.d("TAG", "onResponse: "+ response.body());
                    Float value1 = valuePojo.getConversion_rate();
                    Log.d("TAG", "onResponse: "+ value1);
                    value.setValue(value1);
                }
            }
            @Override
            public void onFailure(Call<ValuePojo> call, Throwable t) {
                Log.e("ggssss", t.toString()+ call);
                Log.d("ggssss","123");
            }
        });
        return value;
    }

}
