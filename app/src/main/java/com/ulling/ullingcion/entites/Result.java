package com.ulling.ullingcion.entites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("86400")
    @Expose
    private List<List<Integer>> _86400 = null;

    public List<List<Integer>> get86400() {
        return _86400;
    }

    public void set86400(List<List<Integer>> _86400) {
        this._86400 = _86400;
    }
}
