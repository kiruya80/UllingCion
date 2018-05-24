package com.ulling.ullingcion.model;


import android.arch.lifecycle.MutableLiveData;

import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.LineMsgResponse;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;
import com.ulling.ullingcion.network.RetrofitLineService;
import com.ulling.ullingcion.network.RetrofitUsdToKrwService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel {
    private static MainModel sInstance;

    private MutableLiveData<List<UpbitUsdToKrwResponse>> upbitUsdToKrwResponse = null;

    public MainModel() {
        super();
    }

    public static MainModel getInstance() {
        if (sInstance == null) {
            synchronized (MainModel.class) {
                if (sInstance == null) {
                    sInstance = new MainModel();
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<List<UpbitUsdToKrwResponse>> getUsdToKrw() {
        if (upbitUsdToKrwResponse == null) {
            upbitUsdToKrwResponse = new MutableLiveData<List<UpbitUsdToKrwResponse>>();
        } else {
            return upbitUsdToKrwResponse;
        }
        return upbitUsdToKrwResponse;
    }


    // https://quotation-api-cdn.dunamu.com/v1/forex/recent?codes=FRX.KRWUSD
    public void loadUsdToKrw() {
        Call<List<UpbitUsdToKrwResponse>> call = RetrofitUsdToKrwService.getInstance().getUsdToKrw();
        call.enqueue(new Callback<List<UpbitUsdToKrwResponse>>() {
            @Override
            public void onResponse(Call<List<UpbitUsdToKrwResponse>> call, Response<List<UpbitUsdToKrwResponse>> response) {
                QcLog.e("onResponse === " + response.toString());

                if (response.isSuccessful()) {
                    QcLog.e("onResponse === isSuccessful ");
                    List<UpbitUsdToKrwResponse> result = response.body();
                    upbitUsdToKrwResponse.postValue(result);

                } else {
                    QcLog.e("onResponse === false");
                }
            }

            @Override
            public void onFailure(Call<List<UpbitUsdToKrwResponse>> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());

                upbitUsdToKrwResponse = new MutableLiveData<List<UpbitUsdToKrwResponse>>();
            }
        });
    }

}
