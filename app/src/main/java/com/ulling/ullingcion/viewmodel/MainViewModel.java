package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ulling.lib.core.viewmodel.QcBaseViewModel;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;
import com.ulling.ullingcion.model.MainModel;

import java.util.List;

public class MainViewModel extends QcBaseViewModel {

    private MainModel mainModel;

    private MutableLiveData<List<UpbitUsdToKrwResponse>> upbitUsdToKrwResponse = null;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }


    public LiveData<List<UpbitUsdToKrwResponse>> getUsdToKrw( ) {
        if (upbitUsdToKrwResponse == null && mainModel != null) {
            upbitUsdToKrwResponse = mainModel.getUsdToKrw( );
        }
        return upbitUsdToKrwResponse;
    }

    public void loadUsdToKrw() {
        if (mainModel != null) {
            mainModel.loadUsdToKrw();
        }
    }
}
