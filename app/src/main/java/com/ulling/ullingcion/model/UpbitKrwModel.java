package com.ulling.ullingcion.model;

import android.arch.lifecycle.MutableLiveData;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
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

    public void loadKrwList(final String code, String count, String to) {
        QcLog.e("code " + code + " , time " + to);
        Call<List<UpbitPriceResponse>> call = RetrofitUpbitService.getInstance().getKrwCoinAnswers(code, count, to);
        call.enqueue(new Callback<List<UpbitPriceResponse>>() {
            @Override
            public void onResponse(Call<List<UpbitPriceResponse>> call, Response<List<UpbitPriceResponse>> response) {
                QcLog.e("message == " + response.message().toString());
                int statusCode = response.code();
                QcLog.e("onResponse statusCode !!! " + statusCode);
                if (response.isSuccessful()) {
                    QcLog.e("onResponse isSuccessful == ");
                    upbitPriceList.postValue(response.body());
                } else {
                    try {
//                    {"status":404,"error":"Not Found","message":"Not Found","timeStamp":"Sun Apr 29 03:02:57 KST 2018","trace":null}
                        UpbitErrorResponse mError = (UpbitErrorResponse) RetrofitUpbitService.retrofit.responseBodyConverter(
                                UpbitErrorResponse.class, UpbitErrorResponse.class.getAnnotations())
                                .convert(response.errorBody());

                        UpbitPriceResponse mUpbitPriceResponse = new UpbitPriceResponse();
                        mUpbitPriceResponse.setType(QcRecyclerBaseAdapter.TYPE_ERROR);
                        mUpbitPriceResponse.setCode(code);
                        mUpbitPriceResponse.setErrorResponse(mError);

                        List<UpbitPriceResponse> list = upbitPriceList.getValue();
                        if (list == null)
                         list = new ArrayList<>();
                        list.add(mUpbitPriceResponse);

                        upbitPriceList.postValue(list);
                        QcLog.e("UpbitErrorResponse ==== " + mError.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UpbitPriceResponse>> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString());
                QcLog.e("onFailure error loading from API == " + t.getMessage());

            }
        });
    }
}
