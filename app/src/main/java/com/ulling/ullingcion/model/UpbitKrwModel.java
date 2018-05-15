package com.ulling.ullingcion.model;

import android.arch.lifecycle.MutableLiveData;

import com.ulling.lib.core.network.QcBaseRetrofitService;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.ullingcion.common.ApiUrl;
import com.ulling.ullingcion.entites.Status;
import com.ulling.ullingcion.entites.UpbitErrorResponse;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.network.RetrofitUpbitService;
import com.ulling.ullingcion.view.adapter.UpbitKrwAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpbitKrwModel {
    private static UpbitKrwModel sInstance;

    private MutableLiveData<List<UpbitPriceResponse>> upbitPriceList = null;

    public UpbitKrwModel() {
        super();
    }

    public static UpbitKrwModel getInstance() {
        if (sInstance == null) {
            synchronized (UpbitKrwModel.class) {
                if (sInstance == null) {
                    sInstance = new UpbitKrwModel();
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<List<UpbitPriceResponse>> getKrwList() {
        if (upbitPriceList == null) {
            upbitPriceList = new MutableLiveData<List<UpbitPriceResponse>>();
//            // 로컬에서 가져오는 경우
//            if (mDatabase != null) {
//                LiveData<QcBaseItem> user_ = mDatabase.getUser();
//                user.postValue(user_.getValue());
//            }
            // 로컬에서 가져오는 경우
//            if (mDatabase != null) {
//                LiveData<QcBaseItem> user_ = mDatabase.exUserDao().loadUser();
//                user.postValue(user_.getValue());
//            }
        } else {
            return upbitPriceList;
        }
        return upbitPriceList;
    }

    public void loadKrwList(final String coinSymbol, String count, String to,
                            final QcBaseRetrofitService.OnRetrofitListener onRetrofitListener) {
        QcLog.e("coinSymbol " + coinSymbol + " , time " + to);
        Call<List<UpbitPriceResponse>> call = RetrofitUpbitService.getInstance().getKrwCoinAnswers("CRIX.UPBIT.KRW-" + coinSymbol, count, to);
        call.enqueue(new Callback<List<UpbitPriceResponse>>() {
            @Override
            public void onResponse(Call<List<UpbitPriceResponse>> call, Response<List<UpbitPriceResponse>> response) {
                QcLog.e("message == " + response.code() + " , " + response.message().toString());
                if (response.isSuccessful()) {
//                    QcLog.e("onResponse isSuccessful == " + response.body());
                    List<UpbitPriceResponse> result = response.body();
                    if (result != null && result.size() == 0) {
                        // 원화 상장 예정 []
                        UpbitPriceResponse mUpbitPriceResponse = new UpbitPriceResponse();
                        mUpbitPriceResponse.setCode(coinSymbol);
                        mUpbitPriceResponse.setLogoImgUrl(ApiUrl.BASE_COIN_LOGO_URL + coinSymbol + ".png");
                        mUpbitPriceResponse.setTimestamp(System.currentTimeMillis());
                        mUpbitPriceResponse.setHighPrice(coinSymbol);
                        mUpbitPriceResponse.setLowPrice("원화상장 예정");

                        result.add(mUpbitPriceResponse);

                        upbitPriceList.postValue(result);
                    }
                    if (onRetrofitListener != null)
                        onRetrofitListener.onSuccessful();

                } else {
                    UpbitErrorResponse mError = new UpbitErrorResponse(response.errorBody().toString());
                    try {
//                    {"status":404,"error":"Not Found","message":"Not Found","timeStamp":"Sun Apr 29 03:02:57 KST 2018","trace":null}
                          mError = (UpbitErrorResponse) RetrofitUpbitService.retrofit.responseBodyConverter(
                                UpbitErrorResponse.class, UpbitErrorResponse.class.getAnnotations())
                                .convert(response.errorBody());
//                        QcLog.e("UpbitErrorResponse ==== " + mError.toString());

                        // 테스트용
//                        UpbitPriceResponse mUpbitPriceResponse = new UpbitPriceResponse();
//                        mUpbitPriceResponse.setType(UpbitKrwAdapter.TYPE_ERROR);
//                        mUpbitPriceResponse.setCode(coinSymbol);
//                        mUpbitPriceResponse.setLogoImgUrl(ApiUrl.BASE_COIN_LOGO_URL + coinSymbol + ".png");
//                        mUpbitPriceResponse.setErrorResponse(mError);
//
//                        List<UpbitPriceResponse> list = upbitPriceList.getValue();
//                        if (list == null)
//                            list = new ArrayList<>();
//                        list.add(mUpbitPriceResponse);
//
//                        upbitPriceList.postValue(list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (onRetrofitListener != null)
                        onRetrofitListener.onErrorBody(mError);
                }
            }

            @Override
            public void onFailure(Call<List<UpbitPriceResponse>> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());
                UpbitErrorResponse mError = new UpbitErrorResponse(t.getMessage());
                if (onRetrofitListener != null)
                    onRetrofitListener.onErrorBody(mError);
            }
        });
    }
}
