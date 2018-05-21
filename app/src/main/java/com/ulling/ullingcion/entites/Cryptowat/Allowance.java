package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allowance {

    @SerializedName("cost")
    @Expose
    private long cost;
    @SerializedName("remaining")
    @Expose
    private long remaining;

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "Allowance{" +
                "cost=" + cost +
                ", remaining=" + remaining +
                '}';
    }
}
