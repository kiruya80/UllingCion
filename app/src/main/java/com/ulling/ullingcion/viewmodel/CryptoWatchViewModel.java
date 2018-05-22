package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ulling.lib.core.viewmodel.QcBaseViewModel;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CandlesLine;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.model.CryptoWatchModel;

import java.util.List;

/**
 * ViewModel은 앱의 View를 위한 model입니다. View가 추상화 된 것이죠.
 * ViewModel은 DataModel로부터 필요한 데이터를 받고, UI 로직을 적용한 뒤 View가 소비하는 데이터를 노출시킵니다.
 * DataModel과 비슷하게, ViewModel은 Observable을 통해서 데이터를 노출시킵니다.
 */
public class CryptoWatchViewModel extends QcBaseViewModel {

    private CryptoWatchModel cryptoWatchModel;

    private MutableLiveData<CryptowatSummary> cryptowatSummary = null;
    private MutableLiveData<CryptoWatchCandles> cryptoWatchCandles = null;
    private MutableLiveData<List<Candles>> candles = null;
    private MutableLiveData<List<CandlesLine>> candlesSupportLine = null;

    public CryptoWatchViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewModel(CryptoWatchModel cryptoWatchModel) {
        this.cryptoWatchModel = cryptoWatchModel;
    }


    public LiveData<List<CandlesLine>> getCandlesSupportLine() {
        if (candlesSupportLine == null && cryptoWatchModel != null) {
            candlesSupportLine = cryptoWatchModel.getCandlesSupportLine();
        }
        return candlesSupportLine;
    }

    public void setCandlesSupportLine(int supportCount, double minPrice) {
        if (cryptoWatchModel != null) {
            cryptoWatchModel.setCandlesSupportLine(supportCount, minPrice);
        }
    }


    public LiveData<CryptowatSummary> getSummary() {
        if (cryptowatSummary == null && cryptoWatchModel != null) {
            cryptowatSummary = cryptoWatchModel.getSummary();
        }
        return cryptowatSummary;
    }

    public void loadSummary() {
        if (cryptoWatchModel != null) {
            cryptoWatchModel.loadSummary();
        }
    }

    public LiveData<CryptoWatchCandles> getCandlesStick() {
        if (cryptoWatchCandles == null && cryptoWatchModel != null) {
            cryptoWatchCandles = cryptoWatchModel.getCandlesStick();
        }
        return cryptoWatchCandles;
    }

    public LiveData<List<Candles>> getCandles() {
        getCandlesStick();
        if (candles == null && cryptoWatchModel != null) {
            candles = cryptoWatchModel.getCandles();
        }
        return candles;
    }


    public void loadCandlesStick(long after, int periods) {
        if (cryptoWatchModel != null) {
            cryptoWatchModel.loadCandlesStick(after, periods);
        }
    }

}
