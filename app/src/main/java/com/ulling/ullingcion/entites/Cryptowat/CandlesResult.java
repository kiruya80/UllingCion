package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CandlesResult {


    @SerializedName("60")
    @Expose
    private List<List<String>> candles_1M = null;
    @SerializedName("180")
    @Expose
    private List<List<String>> candles_3M = null;
    @SerializedName("300")
    @Expose
    private List<List<String>> candles_5M = null;
    @SerializedName("900")
    @Expose
    private List<List<String>> candles_15M = null;
    @SerializedName("1800")
    @Expose
    private List<List<String>> candles_30M = null;


    @SerializedName("3600")
    @Expose
    private List<List<String>> candles_1H = null;

    @SerializedName("7200")
    @Expose
    private List<List<String>> candles_2H = null;

    @SerializedName("14400")
    @Expose
    private List<List<String>> candles_4H = null;

    @SerializedName("21600")
    @Expose
    private List<List<String>> candles_6H = null;

    @SerializedName("43200")
    @Expose
    private List<List<String>> candles_12H = null;

    @SerializedName("86400")
    @Expose
    private List<List<String>> candles_1D = null;

    @SerializedName("259200")
    @Expose
    private List<List<String>> candles_2D = null;

    @SerializedName("604800")
    @Expose
    private List<List<String>> candles_1W = null;

    public List<List<String>> getCandles_1M() {
        return candles_1M;
    }

    public void setCandles_1M(List<List<String>> candles_1M) {
        this.candles_1M = candles_1M;
    }

    public List<List<String>> getCandles_3M() {
        return candles_3M;
    }

    public void setCandles_3M(List<List<String>> candles_3M) {
        this.candles_3M = candles_3M;
    }

    public List<List<String>> getCandles_5M() {
        return candles_5M;
    }

    public void setCandles_5M(List<List<String>> candles_5M) {
        this.candles_5M = candles_5M;
    }

    public List<List<String>> getCandles_15M() {
        return candles_15M;
    }

    public void setCandles_15M(List<List<String>> candles_15M) {
        this.candles_15M = candles_15M;
    }

    public List<List<String>> getCandles_30M() {
        return candles_30M;
    }

    public void setCandles_30M(List<List<String>> candles_30M) {
        this.candles_30M = candles_30M;
    }

    public List<List<String>> getCandles_1H() {
        return candles_1H;
    }

    public void setCandles_1H(List<List<String>> candles_1H) {
        this.candles_1H = candles_1H;
    }

    public List<List<String>> getCandles_2H() {
        return candles_2H;
    }

    public void setCandles_2H(List<List<String>> candles_2H) {
        this.candles_2H = candles_2H;
    }

    public List<List<String>> getCandles_4H() {
        return candles_4H;
    }

    public void setCandles_4H(List<List<String>> candles_4H) {
        this.candles_4H = candles_4H;
    }

    public List<List<String>> getCandles_6H() {
        return candles_6H;
    }

    public void setCandles_6H(List<List<String>> candles_6H) {
        this.candles_6H = candles_6H;
    }

    public List<List<String>> getCandles_12H() {
        return candles_12H;
    }

    public void setCandles_12H(List<List<String>> candles_12H) {
        this.candles_12H = candles_12H;
    }

    public List<List<String>> getCandles_1D() {
        return candles_1D;
    }

    public void setCandles_1D(List<List<String>> candles_1D) {
        this.candles_1D = candles_1D;
    }

    public List<List<String>> getCandles_2D() {
        return candles_2D;
    }

    public void setCandles_2D(List<List<String>> candles_2D) {
        this.candles_2D = candles_2D;
    }

    public List<List<String>> getCandles_1W() {
        return candles_1W;
    }

    public void setCandles_1W(List<List<String>> candles_1W) {
        this.candles_1W = candles_1W;
    }
}
