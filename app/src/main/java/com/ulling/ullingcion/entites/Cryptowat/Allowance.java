package com.ulling.ullingcion.entites.Cryptowat;

public class Allowance {
    private String remaining;

    private String cost;

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "\nremaining='" + remaining +
                "\ncost='" + cost;
    }
}
