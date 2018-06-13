package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewmodel.QcBaseViewModel;
import com.ulling.ullingcion.db.DatabaseCreator;
import com.ulling.ullingcion.db.RoomLocalData;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CandlesDao;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;
import com.ulling.ullingcion.model.MainModel;

import java.util.List;

public class MainViewModel extends QcBaseViewModel {

    private MainModel mainModel;

    private MutableLiveData<List<UpbitUsdToKrwResponse>> upbitUsdToKrwResponse = null;
    private RoomLocalData localData;
    private CandlesDao candlesDao;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewModel(MainModel mainModel) {
        this.mainModel = mainModel;
        initLocalDb();
    }

    private void initLocalDb() {
        localData = DatabaseCreator.getRoomLocalData(getApplication().getBaseContext());

        candlesDao = localData.candlesDatabase();
    }

    public long insertCandle(Candles candle) {
        if (candlesDao != null) {
            long resultIndex = candlesDao.insertCandle(candle);
            return resultIndex;
        } else {
            return -1;
        }
    }


    public LiveData<List<Candles>> getAllCandles() {
        if (candlesDao != null) {
            return candlesDao.getAllCandles();
        } else {
            return null;
        }
    }

    /**
     * 환율가져오기
     *
     * @return
     */
    public LiveData<List<UpbitUsdToKrwResponse>> getUsdToKrw() {
        if (upbitUsdToKrwResponse == null && mainModel != null) {
            upbitUsdToKrwResponse = mainModel.getUsdToKrw();
        }
        return upbitUsdToKrwResponse;
    }

    public void loadUsdToKrw() {
        if (mainModel != null) {
            mainModel.loadUsdToKrw();
        }
    }
}
