package com.example.vhodnaiaprproga.repository.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIMoney {
    @GET("pair/{firstValue}")
    Call<ValuePojo> getMultiplier(@Path("firstValue") String firstValue);

}
