package com.ulling.ullingcion.entites.Cryptowat;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ulling.lib.core.entities.QcBaseItem;


import java.io.Serializable;
import java.math.BigDecimal;


@Entity
public class Candles extends QcBaseItem implements Serializable {
    @NonNull @PrimaryKey
    public long id;
    public long closeTime;
    public double openPrice;
    public double highPrice;
    public double lowPrice;
    public double closePrice;
    public double volume;

//    @Ignore
//    public Calculations calculations;

    public Candles() {
    }

    public Candles(long closeTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume) {
        super();
        this.closeTime = closeTime;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Candles{" +
                "id=" + id +
                ", closeTime=" + closeTime +
                ", openPrice=" + openPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", closePrice=" + closePrice +
                ", volume=" + volume +
                '}';
    }
}
