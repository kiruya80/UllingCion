package com.ulling.ullingcion.entites.Cryptowat;

import com.ulling.lib.core.entities.QcBaseItem;


import java.math.BigDecimal;

public class Candles extends QcBaseItem {
    private long closeTime;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
    private double closePrice;
    private double volume;

    public Calculations calculations = new Calculations();

    public class Calculations {
        public double rs = 0;
        public double rsi = 0;
        public double rsAvgGain = 0;
        public double rsAvgLoss = 0;
        public double change = 0;

        public double getRs() {
            return rs;
        }

        public void setRs(double rs) {
            this.rs = rs;
        }

        public double getRsi() {
            return rsi;
        }

        public void setRsi(double rsi) {
            this.rsi = rsi;
        }

        public double getRsAvgGain() {
            return rsAvgGain;
        }

        public void setRsAvgGain(double rsAvgGain) {
            this.rsAvgGain = rsAvgGain;
        }

        public double getRsAvgLoss() {
            return rsAvgLoss;
        }

        public void setRsAvgLoss(double rsAvgLoss) {
            this.rsAvgLoss = rsAvgLoss;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        @Override
        public String toString() {
            return "Calculations{" +
                    "rs=" + rs +
                    ", rsi=" + rsi +
                    ", rsAvgGain=" + rsAvgGain +
                    ", rsAvgLoss=" + rsAvgLoss +
                    ", change=" + change +
                    '}';
        }
    }
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

    public Calculations getCalculations() {
        return calculations;
    }

    public void setCalculations(Calculations calculations) {
        this.calculations = calculations;
    }

    @Override
    public String toString() {
        return "Candles{" +
                "closeTime=" + closeTime +
                ", openPrice=" + openPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", closePrice=" + closePrice +
                ", volume=" + volume +
                ", calculations=" + calculations +
                '}';
    }
}
