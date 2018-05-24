package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("last")
    @Expose
    private double last;
    @SerializedName("high")
    @Expose
    private double high;
    @SerializedName("low")
    @Expose
    private double low;
    @SerializedName("change")
    @Expose
    private Change change;

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "Price{" +
                "last=" + last +
                ", high=" + high +
                ", low=" + low +
                ", change=" + change +
                '}';
    }
}

