package com.ulling.ullingcion.model;


import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.entites.LineMsgResponse;
import com.ulling.ullingcion.network.RetrofitLineService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineModel {
    private static LineModel sInstance;

    public LineModel() {
        super();
    }

    public static LineModel getInstance() {
        if (sInstance == null) {
            synchronized (LineModel.class) {
                if (sInstance == null) {
                    sInstance = new LineModel();
                }
            }
        }
        return sInstance;
    }


    public void sendMsg(Map<String, String> params) {
        QcLog.e("sendMsg ===  " + params.toString());
        Call<LineMsgResponse> call = RetrofitLineService.getInstance().sendMsg(params);
        call.enqueue(new Callback<LineMsgResponse>() {
            @Override
            public void onResponse(Call<LineMsgResponse> call, Response<LineMsgResponse> response) {
                QcLog.e("onResponse === " + response.toString());

                if (response.isSuccessful()) {
                    QcLog.e("onResponse === isSuccessful ");

                } else {
                    QcLog.e("onResponse === false");
                }
            }

            @Override
            public void onFailure(Call<LineMsgResponse> call, Throwable t) {
                QcLog.e("onFailure error loading from API == " + t.toString() + " , " + t.getMessage());
            }
        });
    }

}
