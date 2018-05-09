package com.ulling.ullingcion;

import android.app.Activity;
import android.app.ActivityManager;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import android.arch.lifecycle.MutableLiveData;

import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;
import com.ulling.ullingcion.db.UpbitRoomDatabase;

/**
 * Created by KILHO on 2016. 7. 4..
 */
public class QUllingApplication extends QcBaseApplication {
    private static QUllingApplication SINGLE_U;
    public static Context qCon;
    private MutableLiveData<Boolean> isUpbitService = null;
//    public static QcPreferences appQcPreferences;

    @Override
    public void onCreate() {
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
        }
        super.onCreate();
        init();
    }

    /**
     * @MethodName : init
     * @Date : 2015. 3. 15.
     * @author : KILHO
     * @Method ㄴ 초기화
     * @변경이력
     */
    private synchronized void init() {
        SINGLE_U = this;
        qCon = getApplicationContext();
        APP_NAME = qCon.getResources().getString(R.string.app_name);
        QcPreferences.getInstance().getAPP_NAME();
        QcToast.getInstance().show("Start App : " + QcPreferences.getInstance().getAPP_NAME(), false);
        initIsUpbitService();
    }

    public static synchronized QUllingApplication getInstance() {
        return SINGLE_U;
    }
//    public static synchronized QcPreferences getQcPrefer() {
//        if (appQcPreferences == null) {
//            appQcPreferences = QcPreferences.getInstance(qCon, APP_NAME);
//        }
//        return appQcPreferences;
//    }

    public UpbitRoomDatabase getDatabase() {
        return UpbitRoomDatabase.getInstance(this, mAppExecutors);
    }

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("UpbitService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public MutableLiveData<Boolean> initIsUpbitService() {
        if (isUpbitService == null) {
            isUpbitService = new MutableLiveData<Boolean>();
        }
        isUpbitService.postValue(isServiceRunningCheck());
        QcLog.e("isServiceRunningCheck() == " + isServiceRunningCheck());
        return isUpbitService;
    }
    public MutableLiveData<Boolean> getIsUpbitService() {
        if (isUpbitService == null) {
            isUpbitService = new MutableLiveData<Boolean>();
//            isUpbitService.postValue(false);
            initIsUpbitService();
        }
        return isUpbitService;
    }

    public void setIsUpbitService(MutableLiveData<Boolean> isUpbitService) {
        this.isUpbitService = isUpbitService;
    }
}