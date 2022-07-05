package com.example.appbanco.model;


import com.example.appbanco.adapter.Quote;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataModel implements Serializable {

    //Passo 1
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("symbol")
    @Expose
    public String symbol;
    @SerializedName("price_usd")
    @Expose
    public String price_usd;
    @SerializedName("percent_change_1h")
    @Expose
    public String percent_change_1h;
    @SerializedName("percent_change_24h")
    @Expose
    public String percent_change_24h;
    @SerializedName("percent_change_7d")
    @Expose
    public String percent_change_7d;
    @SerializedName("quote")
    @Expose
    public Quote quote;
    private final static long serialVersionUID = 991796161238960817L;
    public DataModel() {
    }

    public DataModel(int id, String name, String symbol, String price_usd, String percent_change_1h, String percent_change_24h, String percent_change_7d, Quote quote) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price_usd = price_usd;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.quote = quote;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public String getPercent_change_1h() {
        return percent_change_1h;
    }

    public String getPercent_change_24h() {
        return percent_change_24h;
    }

    public String getPercent_change_7d() {
        return percent_change_7d;
    }

    public Quote getQuote() {
        return quote;
    }
}
