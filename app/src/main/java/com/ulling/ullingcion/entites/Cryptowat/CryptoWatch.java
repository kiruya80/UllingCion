package com.ulling.ullingcion.entites.Cryptowat;

import com.ulling.lib.core.entities.QcBaseItem;

public class CryptoWatch extends QcBaseItem {
    private int CloseTime;
    private int OpenPrice;
    private int HighPrice;
    private int LowPrice;
    private int ClosePrice;
    private int Volume;
    private int rsi;

    public int getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(int closeTime) {
        CloseTime = closeTime;
    }

    public int getOpenPrice() {
        return OpenPrice;
    }

    public void setOpenPrice(int openPrice) {
        OpenPrice = openPrice;
    }

    public int getHighPrice() {
        return HighPrice;
    }

    public void setHighPrice(int highPrice) {
        HighPrice = highPrice;
    }

    public int getLowPrice() {
        return LowPrice;
    }

    public void setLowPrice(int lowPrice) {
        LowPrice = lowPrice;
    }

    public int getClosePrice() {
        return ClosePrice;
    }

    public void setClosePrice(int closePrice) {
        ClosePrice = closePrice;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
    }

    public int getRsi() {
        return rsi;
    }

    public void setRsi(int rsi) {
        this.rsi = rsi;
    }

    @Override
    public String toString() {
        return "CryptoWatch{" +
                "CloseTime=" + CloseTime +
                ", OpenPrice=" + OpenPrice +
                ", HighPrice=" + HighPrice +
                ", LowPrice=" + LowPrice +
                ", ClosePrice=" + ClosePrice +
                ", Volume=" + Volume +
                ", rsi=" + rsi +
                '}';
    }
}
