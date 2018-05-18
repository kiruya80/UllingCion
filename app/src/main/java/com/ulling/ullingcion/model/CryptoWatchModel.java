package com.ulling.ullingcion.model;

import android.arch.lifecycle.MutableLiveData;

import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.network.RetrofitCryptowatService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * DatModel — 데이터 소스를 추상화합니다.
 *
 * DataModel은 데이터를 이벤트 스트림을 통해서 소비 가능하게(consumable) 노출시킵니다. RxJava의 Observable 을 이용해서 말이죠.
 * 그것은 네트워크 계층이나 데이터베이스 또는 shared preferences 등의 다양한 소스로 부터 데이터를 구성합니다.
 * 그리고 쉽게 소비가능한 데이터를 누구든지 필요한 것들에 노출시킵니다. DataModel은 모든 비지니스 로직을 가지고 있게 됩니다.

 단일 책임 법칙(single responsibility principle)에 대해서 우리가 강조하는 것은 나중에 DataModel을 만들도록 이끌 것입니다.
 예를들어, 출력값을 API 서비스와 데이터베이스 계층으로부터 받아와 구성하는 ArticleDataModel이 있다고 합시다.
 이 DataModel은 age filter를 적용하여 최근의 뉴스들이 데이터베이스로부터 받아지도록 하기 위해서 비지니스 로직을 다루게 됩니다.
 */
public class CryptoWatchModel {
    private static CryptoWatchModel sInstance;

    private MutableLiveData<CryptowatSummary> cryptowatSummary = null;

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


    public void loadSummary() {
        QcLog.e("loadSummary ===  "  );
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

}
