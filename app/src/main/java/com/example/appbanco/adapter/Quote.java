package com.example.appbanco.adapter;


import com.example.appbanco.model.USD;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Quote implements Serializable
{

    @SerializedName("USD")
    @Expose
    private USD uSD;
    private final static long serialVersionUID = -2001593208458430355L;

    public USD getUSD() {
        return uSD;
    }

    public void setUSD(USD uSD) {
        this.uSD = uSD;
    }

}