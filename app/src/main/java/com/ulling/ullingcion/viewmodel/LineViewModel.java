package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ulling.lib.core.network.QcBaseRetrofitService;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewmodel.QcBaseViewModel;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.LineModel;
import com.ulling.ullingcion.model.UpbitKrwModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class LineViewModel extends QcBaseViewModel {

    private LineModel lineModel;

    public LineViewModel(@NonNull Application application) {
        super(application);
    }

    public void setViewModel(LineModel lineModel) {
        this.lineModel = lineModel;
    }

    public void sendMsg(String message) {
        if (lineModel != null) {
//            UserInfo mInfo=new UserInfo("countyname","android");
//            String request=new Gson().toJson(mUserInfo);
//
//            //Here the json data is add to a hash map with key data
//            Map<String,String> params = new HashMap<String, String>();
//            params.put("data", request);

            Map<String, String> params = new HashMap<String, String>();
            params.put("message", message);
            lineModel.sendMsg(params);
        }
    }


    public void sendMsgAndImg(String message, String imgUrl) {
        if (lineModel != null) {
//            UserInfo mInfo=new UserInfo("countyname","android");
//            String request=new Gson().toJson(mUserInfo);
//
//            //Here the json data is add to a hash map with key data
//            Map<String,String> params = new HashMap<String, String>();
//            params.put("data", request);

            Map<String, String> params = new HashMap<String, String>();
            params.put("message", message);
            if (!imgUrl.isEmpty())
            params.put("imageThumbnail", imgUrl);
            if (!imgUrl.isEmpty())
            params.put("imageFullsize", imgUrl);
            lineModel.sendMsg(params);
        }
    }
}
