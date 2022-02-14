package com.example.vhodnaiaprproga.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vhodnaiaprproga.repository.Network.RetrofitClass;

public class MainViewModel extends AndroidViewModel {

    RetrofitClass retrofitClass = new RetrofitClass();
    private LiveData<Float> valueMux;


    public MainViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<Float> getValue(String currency){return retrofitClass.getValue(currency);}
}
