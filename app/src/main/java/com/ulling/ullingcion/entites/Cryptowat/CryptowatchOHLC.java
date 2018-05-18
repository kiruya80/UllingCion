package com.ulling.ullingcion.entites.Cryptowat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ulling.lib.core.entities.QcBaseItem;
import com.ulling.ullingcion.entites.Allowance;
import com.ulling.ullingcion.entites.Result;

public class CryptowatchOHLC extends QcBaseItem {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("allowance")
    @Expose
    private Allowance allowance;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Allowance getAllowance() {
        return allowance;
    }

    public void setAllowance(Allowance allowance) {
        this.allowance = allowance;
    }
}
