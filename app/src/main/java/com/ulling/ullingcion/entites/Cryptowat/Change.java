package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Change {


    @SerializedName("percentage")
    @Expose
    private double percentage;
    @SerializedName("absolute")
    @Expose
    private double absolute;

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getAbsolute() {
        return absolute;
    }

    public void setAbsolute(double absolute) {
        this.absolute = absolute;
    }

    @Override
    public String toString() {
        return "Change{" +
                "percentage=" + percentage +
                ", absolute=" + absolute +
                '}';
    }
}
