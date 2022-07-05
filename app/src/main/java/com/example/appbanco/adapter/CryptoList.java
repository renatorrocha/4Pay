package com.example.appbanco.adapter;


import com.example.appbanco.model.DataModel;
import com.google.android.gms.common.api.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CryptoList implements Serializable
{

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private List<DataModel> data = null;
    private final static long serialVersionUID = -4369048252305703014L;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }

}