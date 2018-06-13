package com.ulling.ullingcion;

import android.app.Activity;
import android.app.ActivityManager;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;

import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;
import com.ulling.ullingcion.db.UpbitRoomDatabase;
import com.ulling.ullingcion.service.MainService;
import com.ulling.ullingcion.service.UpbitService;

/**
 * Created by KILHO on 2016. 7. 4..
 */
public class QUllingApplication extends QcBaseApplication {
    private static QUllingApplication SINGLE_U;
    public static Context qCon;
    private MutableLiveData<Boolean> isUpbitService = null;

    private final String SERVICE_NAME_MAIN = "MainService";
    private final String SERVICE_NAME_UPBIT = "UpbitService";

    public static synchronized QUllingApplication getInstance() {
        return SINGLE_U;
    }

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
        initMainService();
    }

    private void initMainService() {
        try {

            Intent intent = new Intent(qCon, MainService.class);
            if (isMainServiceRunningCheck()) {
                qCon.stopService(intent);
            }
            qCon.startService(intent);
        } catch (Exception e) {
        }
    }

    public boolean isMainServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SERVICE_NAME_MAIN.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public UpbitRoomDatabase getDatabase() {
        return UpbitRoomDatabase.getInstance(this, mAppExecutors);
    }

    public void initIsUpbitService() {
        if (isUpbitService == null) {
            isUpbitService = new MutableLiveData<Boolean>();
        }
        isUpbitService.postValue(isUpbitServiceRunningCheck());
    }

    public boolean isUpbitServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SERVICE_NAME_UPBIT.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public MutableLiveData<Boolean> getIsUpbitService() {
        if (isUpbitService == null) {
            isUpbitService = new MutableLiveData<Boolean>();
            initIsUpbitService();
        }
        return isUpbitService;
    }


}