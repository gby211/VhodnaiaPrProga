package com.example.vhodnaiaprproga.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vhodnaiaprproga.repository.Network.RetrofitClass;
import com.example.vhodnaiaprproga.repository.Network.ValuePojo;
import com.example.vhodnaiaprproga.repository.roomDB.CurrencyDTO;
import com.example.vhodnaiaprproga.repository.roomDB.RoomRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private RoomRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new RoomRepository(application);
    }

    public LiveData<ValuePojo> getValue(String convertFrom, String convertTo){
        return new RetrofitClass().getValue(convertFrom, convertTo);
    }

    public LiveData<List<CurrencyDTO>> getAllConvertToByConvertFrom(String convertFrom){
        return repository.getAllConvertToByConvertFrom(convertFrom);
    }

    public void insertDTO(String convertFrom, String convertTo, double conversionRate){
        repository.insert(new CurrencyDTO(convertFrom, convertTo, conversionRate));
    }

    public LiveData<List<CurrencyDTO>> getAllConvertFrom(){
        return repository.getAllConvertFrom();
    }

    public LiveData<List<Double>> getByPair(String convertFrom, String convertTo){
        return repository.getByPair(convertFrom, convertTo);
    }

    public void updateConversionRate(double conversionRate, String convertFrom, String convertTo){
        repository.updateConversionRate( conversionRate,  convertFrom,  convertTo);
    }
//    RetrofitClass retrofitClass = new RetrofitClass();
//    private LiveData<Float> valueMux;
//    private RoomRepository repository;
//
//    public MainViewModel(@NonNull Application application) {
//        super(application);
//        repository = new RoomRepository(application);
//    }
//
//    public LiveData<Float> getValue(String currency){return retrofitClass.getValue(currency);}
//    public LiveData<List<CurrencyDTO>> getAllConvertToByConvertFrom(String convertFrom){
//        return repository.getAllConvertToByConvertFrom(convertFrom);
//    }
//
//    public void insertDTO(String convertFrom, String convertTo, double conversionRate){
//        repository.insert(new CurrencyDTO(convertFrom, convertTo, conversionRate));
//    }
//
//    public LiveData<List<CurrencyDTO>> getAllConvertFrom(){
//        return repository.getAllConvertFrom();
//    }
//
//    public LiveData<List<Double>> getByPair(String convertFrom, String convertTo){
//        return repository.getByPair(convertFrom, convertTo);
//    }
}
