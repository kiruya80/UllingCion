package com.ulling.lib.core.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

/**
 * Created by P100651 on 2017-07-04.
 * UI 데이터를 가지는 헬퍼 클래스
 */
public abstract class QcBaseViewModel extends AndroidViewModel {
    public QcBaseViewModel(Application application) {
        super(application);
    }

    protected abstract int createDb();
}
