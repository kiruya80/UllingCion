package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ulling.lib.core.entities.QcBaseItem;

public class CryptoWatchCandles extends QcBaseItem {

    @SerializedName("result")
    @Expose
    private CandlesResult result;

    @SerializedName("allowance")
    @Expose
    private Allowance allowance;

    public CandlesResult getResult() {
        return result;
    }

    public void setResult(CandlesResult result) {
        this.result = result;
    }

    public Allowance getAllowance() {
        return allowance;
    }

    public void setAllowance(Allowance allowance) {
        this.allowance = allowance;
    }

    @Override
    public String toString() {
        return "CryptoWatchCandles{" +
                "result=" + result +
                ", allowance=" + allowance +
                '}';
    }
}
