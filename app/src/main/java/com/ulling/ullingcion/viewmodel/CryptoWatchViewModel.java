package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewmodel.QcBaseViewModel;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.model.CryptoWatchModel;

/**
 *
 * ViewModel은 앱의 View를 위한 model입니다. View가 추상화 된 것이죠.
 * ViewModel은 DataModel로부터 필요한 데이터를 받고, UI 로직을 적용한 뒤 View가 소비하는 데이터를 노출시킵니다.
 * DataModel과 비슷하게, ViewModel은 Observable을 통해서 데이터를 노출시킵니다.
 */
public class CryptoWatchViewModel extends QcBaseViewModel {

    private CryptoWatchModel cryptoWatchModel;

    private MutableLiveData<CryptowatSummary> cryptowatSummary = null;
    private MutableLiveData<CryptoWatchCandles> cryptoWatchCandles = null;

    public CryptoWatchViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewModel(CryptoWatchModel cryptoWatchModel) {
        this.cryptoWatchModel = cryptoWatchModel;
    }


    public LiveData<CryptowatSummary> getSummary( ) {
        if (cryptowatSummary == null && cryptoWatchModel != null) {
            cryptowatSummary = cryptoWatchModel.getSummary( );
        }
        return cryptowatSummary;
    }

    public void loadSummary() {
        if (cryptoWatchModel != null) {
            cryptoWatchModel.loadSummary();
        }
    }

    public LiveData<CryptoWatchCandles> getCandles( ) {
        if (cryptoWatchCandles == null && cryptoWatchModel != null) {
            cryptoWatchCandles = cryptoWatchModel.getCandles( );
        }
        return cryptoWatchCandles;
    }

    public void loadCandlesStick( long after, int periods) {
        if (cryptoWatchModel != null) {
            cryptoWatchModel.loadCandlesStick( after, periods);
        }
    }

}
