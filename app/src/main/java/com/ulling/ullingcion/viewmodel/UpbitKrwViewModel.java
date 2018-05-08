package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ulling.lib.core.viewmodel.QcBaseViewModel;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.UpbitKrwModel;

import java.util.List;

public class UpbitKrwViewModel extends QcBaseViewModel{

    private UpbitKrwModel upbitKrwModel;

    private MutableLiveData<List<UpbitPriceResponse>> upbitPriceList = null;

    public UpbitKrwViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewModel(UpbitKrwModel upbitKrwModel) {
        this.upbitKrwModel = upbitKrwModel;
    }

    public LiveData<List<UpbitPriceResponse>> getKrwList( ) {
        if (upbitPriceList == null && upbitKrwModel != null) {
            upbitPriceList = upbitKrwModel.getKrwList( );
        }
        return upbitPriceList;
    }

    // code=CRIX.UPBIT.KRW-XVG&count=2&to=2018-05-04%2017:30:00
    public void loadKrwList(String coinSymbol, String count, String to) {
        if (upbitKrwModel != null)
        upbitKrwModel.loadKrwList(coinSymbol, count, to);
    }
}
