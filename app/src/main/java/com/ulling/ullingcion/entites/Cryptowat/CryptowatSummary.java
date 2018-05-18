package com.ulling.ullingcion.entites.Cryptowat;

import com.ulling.lib.core.entities.QcBaseItem;

public class CryptowatSummary extends QcBaseItem {
    private Result result = null;

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

    @Override
    public String toString() {
        return "\nCryptowatSummary\n" +
                "result\n" + result ;
    }
}
