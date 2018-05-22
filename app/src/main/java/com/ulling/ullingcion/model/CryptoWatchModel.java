package com.ulling.ullingcion.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CandlesResult;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.network.RetrofitCryptowatService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DatModel — 데이터 소스를 추상화합니다.
 * <p>
 * DataModel은 데이터를 이벤트 스트림을 통해서 소비 가능하게(consumable) 노출시킵니다. RxJava의 Observable 을 이용해서 말이죠.
 * 그것은 네트워크 계층이나 데이터베이스 또는 shared preferences 등의 다양한 소스로 부터 데이터를 구성합니다.
 * 그리고 쉽게 소비가능한 데이터를 누구든지 필요한 것들에 노출시킵니다. DataModel은 모든 비지니스 로직을 가지고 있게 됩니다.
 * <p>
 * 단일 책임 법칙(single responsibility principle)에 대해서 우리가 강조하는 것은 나중에 DataModel을 만들도록 이끌 것입니다.
 * 예를들어, 출력값을 API 서비스와 데이터베이스 계층으로부터 받아와 구성하는 ArticleDataModel이 있다고 합시다.
 * 이 DataModel은 age filter를 적용하여 최근의 뉴스들이 데이터베이스로부터 받아지도록 하기 위해서 비지니스 로직을 다루게 됩니다.
 */
public class CryptoWatchModel {
    private static CryptoWatchModel sInstance;

    private MutableLiveData<CryptowatSummary> cryptowatSummary = null;
    private MutableLiveData<CryptoWatchCandles> cryptoWatchCandles = null;
    private MutableLiveData<List<Candles>> candles = null;

    public CryptoWatchModel() {
        super();
    }

    public static CryptoWatchModel getInstance() {
        if (sInstance == null) {
            synchronized (CryptoWatchModel.class) {
                if (sInstance == null) {
                    sInstance = new CryptoWatchModel();
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<CryptowatSummary> getSummary() {
        if (cryptowatSummary == null) {
            cryptowatSummary = new MutableLiveData<CryptowatSummary>();
        } else {
            return cryptowatSummary;
        }
        return cryptowatSummary;
    }

    public MutableLiveData<CryptoWatchCandles> getCandlesStick() {
        if (cryptoWatchCandles == null) {
            cryptoWatchCandles = new MutableLiveData<CryptoWatchCandles>();
        } else {
            return cryptoWatchCandles;
        }
        return cryptoWatchCandles;
    }


    public MutableLiveData<List<Candles>> getCandles() {
        if (candles == null) {
            candles = new MutableLiveData<List<Candles>>();
        } else {
            return candles;
        }
        return candles;
    }


    public void loadSummary() {
        QcLog.e("loadSummary ===  ");
        Call<CryptowatSummary> call = RetrofitCryptowatService.getInstance().getSummary();
        call.enqueue(new Callback<CryptowatSummary>() {
            @Override
            public void onResponse(Call<CryptowatSummary> call, Response<CryptowatSummary> response) {
                QcLog.e("onResponse === " + response.toString());

                if (response.isSuccessful()) {
                    QcLog.e("onResponse === isSuccessful ");
                    CryptowatSummary result = response.body();
                    cryptowatSummary.postValue(result);
                } else {
                    QcLog.e("onResponse === false");
                    cryptowatSummary = new MutableLiveData<CryptowatSummary>();
                }
            }

            @Override
            public void onFailure(Call<CryptowatSummary> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());

                cryptowatSummary = new MutableLiveData<CryptowatSummary>();
            }
        });
    }

    public void loadCandlesStick(long after, final int periods) {
        QcLog.e("loadCandlesStick ===  " + periods + " , after = " + after);
        Call<CryptoWatchCandles> call = RetrofitCryptowatService.getInstance().getCandlesStick(after, periods);
        call.enqueue(new Callback<CryptoWatchCandles>() {
            @Override
            public void onResponse(Call<CryptoWatchCandles> call, Response<CryptoWatchCandles> response) {
                QcLog.e("onResponse === " + response.toString());

                if (response.isSuccessful()) {
                    QcLog.e("onResponse === isSuccessful ");
                    CryptoWatchCandles result = response.body();
                    cryptoWatchCandles.postValue(result);

                    if (result != null && result.getResult() != null) {
//                        getCandles(periods, result.getResult());
                        new GetCandlesTask(periods, result.getResult()).execute();
                    } else {
                        QcLog.e("getCandles result == null == ");
                    }
                } else {
                    QcLog.e("onResponse === false");
                    cryptoWatchCandles = new MutableLiveData<CryptoWatchCandles>();
                }
            }

            @Override
            public void onFailure(Call<CryptoWatchCandles> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());

                cryptoWatchCandles = new MutableLiveData<CryptoWatchCandles>();
            }
        });
    }


    private class GetCandlesTask extends AsyncTask<Void, Void, Void> {
        private int periods;
        private CandlesResult result;
        private ArrayList<Candles> newCandles = new ArrayList<>();

        public GetCandlesTask(int periods, CandlesResult result) {
            this.periods = periods;
            this.result = result;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newCandles = getCandles(periods, result);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            QcLog.e("getCandles postValue == " + periods);
            candles.postValue(newCandles);
        }
    }

    public ArrayList<Candles> getCandles(int periods, CandlesResult result) {
        ArrayList<Candles> newCandles = new ArrayList<>();

        if (periods == Define.VALUE_CRYPTOWAT_1M) {
            newCandles = setCandles(result.getCandles_1M());

        } else if (periods == Define.VALUE_CRYPTOWAT_3M) {
            newCandles = setCandles(result.getCandles_3M());

        } else if (periods == Define.VALUE_CRYPTOWAT_5M) {
            newCandles = setCandles(result.getCandles_5M());

        } else if (periods == Define.VALUE_CRYPTOWAT_15M) {
            newCandles = setCandles(result.getCandles_15M());

        } else if (periods == Define.VALUE_CRYPTOWAT_30M) {
            newCandles = setCandles(result.getCandles_30M());

        } else if (periods == Define.VALUE_CRYPTOWAT_1H) {
            newCandles = setCandles(result.getCandles_1H());

        } else if (periods == Define.VALUE_CRYPTOWAT_2H) {
            newCandles = setCandles(result.getCandles_2H());

        } else if (periods == Define.VALUE_CRYPTOWAT_4H) {
            newCandles = setCandles(result.getCandles_4H());

        } else if (periods == Define.VALUE_CRYPTOWAT_6H) {
            newCandles = setCandles(result.getCandles_6H());

        } else if (periods == Define.VALUE_CRYPTOWAT_12H) {
            newCandles = setCandles(result.getCandles_12H());

        } else if (periods == Define.VALUE_CRYPTOWAT_1D) {
            newCandles = setCandles(result.getCandles_1D());

        } else if (periods == Define.VALUE_CRYPTOWAT_3D) {
            newCandles = setCandles(result.getCandles_3D());

        } else if (periods == Define.VALUE_CRYPTOWAT_1W) {
            newCandles = setCandles(result.getCandles_1W());
        }
        return newCandles;
    }

    public ArrayList<Candles> setCandles(List<List<String>> candleList) {
        ArrayList<Candles> newCandles = new ArrayList<>();
        if (candleList == null)
            return newCandles;

        for (int i = 0; i < candleList.size(); i++) {
            long closeTime = Long.parseLong(candleList.get(i).get(0)) * 1000;

            Double OpenPrice = Double.parseDouble(candleList.get(i).get(1));
            Double HighPrice = Double.parseDouble(candleList.get(i).get(2));
            Double LowPrice = Double.parseDouble(candleList.get(i).get(3));
            Double ClosePrice = Double.parseDouble(candleList.get(i).get(4));
            Double Volume = Double.parseDouble(candleList.get(i).get(5));

            Candles mCandles = new Candles();
            mCandles.setCloseTime(closeTime);
            mCandles.setOpenPrice(getRound(OpenPrice, 1));
            mCandles.setClosePrice(getRound(ClosePrice, 1));
            mCandles.setHighPrice(getRound(HighPrice, 1));
            mCandles.setLowPrice(getRound(LowPrice, 1));
            mCandles.setVolume(Volume);
            newCandles.add(mCandles);
        }
        return newCandles;
    }

    private double getRound(double value, int number) {
        if (number <= 0)
            return value;

        int intValue = (int) Math.ceil(value);
        int length = (int) (Math.log10(intValue) + 1);
        if (length <= number)
            return value;

        double roundNum = 1;
        for (int i = 0; i < number; i++) {
            roundNum = roundNum * 10d;
        }
        return Math.round(value / roundNum) * roundNum;
    }

}
