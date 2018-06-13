package com.ulling.ullingcion.entites.Cryptowat;

import com.ulling.lib.core.entities.QcBaseItem;

import java.io.Serializable;

public class Calculations extends QcBaseItem implements Serializable {
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