package com.ulling.ullingcion.entites.Cryptowat;

import com.ulling.lib.core.entities.QcBaseItem;

public class CandlesLine extends QcBaseItem {
    private double price = 0;
    private int count = 0;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return  "price = " + price +
                " , count = " + count +
                '}' + "\n\n";
    }
}
