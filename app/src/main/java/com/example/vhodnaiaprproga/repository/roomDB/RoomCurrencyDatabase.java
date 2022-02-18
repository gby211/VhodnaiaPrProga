package com.example.vhodnaiaprproga.repository.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CurrencyDTO.class}, version = 1)
public abstract class RoomCurrencyDatabase extends androidx.room.RoomDatabase {
    public abstract DaoCurrency currencyDAO();

    private static volatile RoomCurrencyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomCurrencyDatabase getDatabase(final Context context){

        if (INSTANCE == null) {

            synchronized (RoomCurrencyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomCurrencyDatabase.class, "currency_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}