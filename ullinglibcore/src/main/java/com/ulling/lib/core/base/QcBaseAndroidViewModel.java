package com.ulling.lib.core.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.ulling.lib.core.util.QcLog;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 8. 3.
 * @description :
 * @since :
 */

public abstract class QcBaseAndroidViewModel extends AndroidViewModel {
    public Context qCon;
    public FragmentActivity qActivity;
    public QcBaseLifeFragment qFrag;

    public QcBaseAndroidViewModel(@NonNull Application application) {
        super(application);
    }

    public void needInitViewModel(FragmentActivity qActivity) {
        QcLog.e("needInitViewModel == ");
        this.qActivity = qActivity;
        this.qCon = qActivity.getApplicationContext();
    }
    public void needInitViewModel(FragmentActivity qActivity, QcBaseLifeFragment qFrag) {
        QcLog.e("needInitViewModel == ");
        this.qActivity = qActivity;
        this.qFrag = qFrag;
        this.qCon = qFrag.qCon;
    }

//    public abstract void needInitViewModel(Context qCon, FragmentActivity qActivity);
    public abstract void needDatabaseModel(int dbTypeLocal, int remoteType, String baseUrl);

    @Override
    protected void onCleared() {
//        if (mDatabaseModel != null)
//            mDatabaseModel.onCleared();
        super.onCleared();
    }
}
