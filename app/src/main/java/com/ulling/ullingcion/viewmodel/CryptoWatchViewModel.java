package com.ulling.ullingcion.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.ulling.lib.core.viewmodel.QcBaseViewModel;

/**
 *
 * ViewModel은 앱의 View를 위한 model입니다. View가 추상화 된 것이죠.
 * ViewModel은 DataModel로부터 필요한 데이터를 받고, UI 로직을 적용한 뒤 View가 소비하는 데이터를 노출시킵니다.
 * DataModel과 비슷하게, ViewModel은 Observable을 통해서 데이터를 노출시킵니다.
 */
public class CryptoWatchViewModel extends QcBaseViewModel {
    public CryptoWatchViewModel(@NonNull Application application) {
        super(application);
    }

}
