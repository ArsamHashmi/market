package com.example.market.Retrofit;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CryptoDetail implements Serializable
{

    @SerializedName("status")
    @Expose
    private com.example.market.Retrofit.Status status;
    @SerializedName("data")
    @Expose
    private JsonObject data = null;
    private final static long serialVersionUID = -4369048252305703014L;

    public com.example.market.Retrofit.Status getStatus() {
        return status;
    }

    public void setStatus(com.example.market.Retrofit.Status status) {
        this.status = status;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

}