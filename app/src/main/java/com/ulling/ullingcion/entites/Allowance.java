package com.ulling.ullingcion.entites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allowance {

    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("remaining")
    @Expose
    private Integer remaining;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

}
