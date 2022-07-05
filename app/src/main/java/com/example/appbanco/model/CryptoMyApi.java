package com.example.appbanco.model;

import com.example.appbanco.adapter.CryptoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CryptoMyApi {


    //https://pro.coinmarketcap.com/api/v1#section/Quick-Start-Guide
    @Headers("X-CMC_PRO_API_KEY:868bc7cb-8ef6-4e10-8a0a-15997afacbf6")
    @GET("/v1/cryptocurrency/listings/latest?")
    Call<CryptoList> doGetUserList(@Query("limit") String page);

//    @GET("v1/cryptocurrency/listings/latest")
//    Call<DataModel>getData();
}
