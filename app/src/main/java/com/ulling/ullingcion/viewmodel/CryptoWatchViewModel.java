package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewmodel.QcBaseViewModel;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CandlesResult;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.model.CryptoWatchModel;

import java.util.ArrayList;
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

    public CryptoWatchViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewModel(CryptoWatchModel cryptoWatchModel) {
        this.cryptoWatchModel = cryptoWatchModel;
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

//    public LiveData<List<Candles>> getCandles(int type) {
//        if (candles == null) {
//            candles = new MutableLiveData<List<Candles>>();
//        }
//        if (cryptoWatchModel != null) {
//            MutableLiveData<CryptoWatchCandles> cryptoWatchCandles = cryptoWatchModel.getCandlesStick();
//            if (cryptoWatchCandles == null)
//                return candles;
//            if (cryptoWatchCandles.getValue() == null)
//                return candles;
//            if (cryptoWatchCandles.getValue().getResult() == null)
//                return candles;
//
//            CandlesResult result = cryptoWatchCandles.getValue().getResult();
//
//            ArrayList<Candles> newCandles = new ArrayList<>();
//
//            if (type == Define.VALUE_CRYPTOWAT_1M) {
//                newCandles = setCandles(result.getCandles_1M());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_3M) {
//                newCandles = setCandles(result.getCandles_3M());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_5M) {
//                newCandles = setCandles(result.getCandles_5M());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_15M) {
//                newCandles = setCandles(result.getCandles_15M());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_30M) {
//                newCandles = setCandles(result.getCandles_30M());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_1H) {
//                newCandles = setCandles(result.getCandles_1H());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_2H) {
//                newCandles = setCandles(result.getCandles_2H());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_4H) {
//                newCandles = setCandles(result.getCandles_4H());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_6H) {
//                newCandles = setCandles(result.getCandles_6H());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_12H) {
//                newCandles = setCandles(result.getCandles_12H());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_1D) {
//                newCandles = setCandles(result.getCandles_1D());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_3D) {
//                newCandles = setCandles(result.getCandles_3D());
//
//            } else if (type == Define.VALUE_CRYPTOWAT_1W) {
//                newCandles = setCandles(result.getCandles_1W());
//            }
//
//            candles.postValue(newCandles);
//        }
//        return candles;
//    }
//
//    public ArrayList<Candles> setCandles(List<List<String>> candleList) {
//        ArrayList<Candles> newCandles = new ArrayList<>();
//        if (candleList == null)
//            return newCandles;
//
//        for (int i = 0; i < candleList.size(); i++) {
//            long closeTime = Long.parseLong(candleList.get(i).get(0)) * 1000;
//
//            Double OpenPrice = Double.parseDouble(candleList.get(i).get(1));
//            Double HighPrice = Double.parseDouble(candleList.get(i).get(2));
//            Double LowPrice = Double.parseDouble(candleList.get(i).get(3));
//            Double ClosePrice = Double.parseDouble(candleList.get(i).get(4));
//            Double Volume = Double.parseDouble(candleList.get(i).get(5));
//
//            Candles mCandles = new Candles();
//            mCandles.setCloseTime(closeTime);
//            mCandles.setOpenPrice(OpenPrice);
//            mCandles.setHighPrice(HighPrice);
//            mCandles.setLowPrice(LowPrice);
//            mCandles.setClosePrice(ClosePrice);
//            mCandles.setVolume(Volume);
//            newCandles.add(mCandles);
//        }
//        return newCandles;
//    }


    public void loadCandlesStick(long after, int periods) {
        if (cryptoWatchModel != null) {
            cryptoWatchModel.loadCandlesStick(after, periods);
        }
    }

}
