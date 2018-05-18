package com.ulling.ullingcion.entites.Cryptowat;

public class Price {
    private String last;

    private Change change;

    private String high;

    private String low;

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    @Override
    public String toString() {
        return "last= " + last +
                "\nhigh= " + high +
                "\nlow= " + low;
    }
}

