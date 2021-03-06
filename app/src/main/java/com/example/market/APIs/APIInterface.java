package com.example.market.APIs;

import com.example.market.Retrofit.CryptoDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {

    //https://pro.coinmarketcap.com/api/v1#section/Quick-Start-Guide
    @Headers("X-CMC_PRO_API_KEY: ")
    @GET("/v1/cryptocurrency/quotes/latest?")
    Call<CryptoDetail> getCoinDetail(@Query("symbol") String coin);

}
