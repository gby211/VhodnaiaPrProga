package com.example.vhodnaiaprproga.repository.roomDB;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class RoomRepository {
    private DaoCurrency currencyDAO;
    private LiveData<List<CurrencyDTO>> allDTO;

    public RoomRepository(Application application){
        RoomCurrencyDatabase database = RoomCurrencyDatabase.getDatabase(application);
        database = Room.databaseBuilder(application.getApplicationContext(), RoomCurrencyDatabase.class, "currency_database")
                .fallbackToDestructiveMigration()
                .build();
//        stockDAO = database.currencyDAO();
//        allStocks = stockDAO.getAllStocks();
        currencyDAO = database.currencyDAO();
    }

    public void insert(CurrencyDTO currencyDTO){
        RoomCurrencyDatabase.databaseWriteExecutor.execute(() -> {
            currencyDAO.addStock(((CurrencyDTO) currencyDTO));
        });
    }

    public void updateConversionRate(double conversionRate, String convertFrom, String convertTo){
        currencyDAO.updateConversionRate( conversionRate,  convertFrom,  convertTo);
    }

    public LiveData<List<CurrencyDTO>> getAllConvertToByConvertFrom(String convertFrom){
        return currencyDAO.getAllConvertToByConvertFrom(convertFrom);
    }

    public LiveData<CurrencyDTO> getById(int id){
        return currencyDAO.getById(id);
    }

    public void delete(CurrencyDTO currencyDTO){
        RoomCurrencyDatabase.databaseWriteExecutor.execute(() -> {
            currencyDAO.deleteStock(((CurrencyDTO) currencyDTO));
        });
    }

    public LiveData<List<CurrencyDTO>> getAllConvertFrom(){

        return currencyDAO.getAllConvertFrom();
    }

    public LiveData<List<Double>> getByPair(String convertFrom, String convertTo){
        return currencyDAO.getByPair(convertFrom, convertTo);
    }

    public LiveData<List<CurrencyDTO>> getAllDTO(){
        return allDTO;
    }
}
