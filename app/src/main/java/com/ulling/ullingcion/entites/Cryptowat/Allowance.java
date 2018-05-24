package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allowance {

    @SerializedName("cost")
    @Expose
    private double cost;

    @SerializedName("remaining")
    @Expose
    private double remaining;

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
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
