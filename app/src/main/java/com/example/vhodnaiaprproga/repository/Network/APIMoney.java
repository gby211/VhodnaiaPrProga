package com.example.vhodnaiaprproga.repository.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIMoney {
    @GET("pair/USD/RUB")
    Call<ValuePojo> getMultiplier(/*@Query("") String firstValue, @Query("/") String secondValue*/);

}
