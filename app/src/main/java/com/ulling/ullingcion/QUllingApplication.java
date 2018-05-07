package com.ulling.ullingcion;

import android.content.Context;

import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;
import com.ulling.ullingcion.db.UpbitRoomDatabase;

/**
 * Created by KILHO on 2016. 7. 4..
 */
public class QUllingApplication extends QcBaseApplication {
    private static QUllingApplication SINGLE_U;
    public static Context qCon;
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
//        MyVolley.init(this);
        QcPreferences.getInstance().getAPP_NAME();
        QcToast.getInstance().show("Start App : " + QcPreferences.getInstance().getAPP_NAME(), false);
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

}