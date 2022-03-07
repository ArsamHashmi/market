package com.example.market.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Quote implements Serializable
{

    @SerializedName("USD")
    @Expose
    private com.example.market.Retrofit.USD uSD;
    private final static long serialVersionUID = -5780538494495942860L;

    public com.example.market.Retrofit.USD getUSD() {
        return uSD;
    }

    public void setUSD(com.example.market.Retrofit.USD uSD) {
        this.uSD = uSD;
    }

}