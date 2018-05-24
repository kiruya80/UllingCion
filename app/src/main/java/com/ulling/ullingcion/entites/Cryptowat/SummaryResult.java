package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SummaryResult {


    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("volume")
    @Expose
    private double volume;
    @SerializedName("volumeQuote")
    @Expose
    private int volumeQuote;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getVolumeQuote() {
        return volumeQuote;
    }

    public void setVolumeQuote(int volumeQuote) {
        this.volumeQuote = volumeQuote;
    }

    @Override
    public String toString() {
        return "SummaryResult{" +
                "price=" + price +
                ", volume=" + volume +
                ", volumeQuote=" + volumeQuote +
                '}';
    }
}
