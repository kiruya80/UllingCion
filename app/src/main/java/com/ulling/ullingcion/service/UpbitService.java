package com.ulling.ullingcion.service;

import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.LineModel;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.viewmodel.LineViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UpbitService extends LifecycleService {
    private final int THREAD_PRIORITY_BACKGROUND = 1000;
    private final int HANDLE_UPBIT = 10;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private QUllingApplication qApp;
    private boolean startService = false;
    private UpbitKrwViewModel mUpbitKrwViewModel;
    private ArrayList<String> coinSymbolList;

    private LineViewModel mLineViewModel;

    public UpbitService() {
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            QcLog.e("handleMessage == " + msg.arg1);
            if (startService) {
                try {
                    // 10분 마다
                    Thread.sleep(3 * 60 * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
//            stopSelf(msg.arg1);
                update();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        QcLog.e("onCreate ==");

//        AndroidInjection.inject(this);
//        tvShowViewModel = new TVShowViewModel(tvShowDataRepo);
//        tvShowViewModel.init(14);

        qApp = QUllingApplication.getInstance();
        startService = true;
        qApp.getIsUpbitService().postValue(true);
        HandlerThread thread = new HandlerThread("ServiceStartArguments", THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        mUpbitKrwViewModel = new UpbitKrwViewModel(qApp);
        mUpbitKrwViewModel.setViewModel(UpbitKrwModel.getInstance());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        QcLog.e("onStartCommand ==");
        QcToast.getInstance().show("Service Start ! ", false);

        mUpbitKrwViewModel.getKrwList().observe(this, new Observer<List<UpbitPriceResponse>>() {
            @Override
            public void onChanged(@Nullable List<UpbitPriceResponse> upbitPriceResponses) {
                QcLog.e("getKrwList observe === ");
                // 원화 상장 예정
                QcToast.getInstance().show("원화 상장 예정 ! ", false);
                if (upbitPriceResponses != null && upbitPriceResponses.size() > 0) {
//                    sendSMS(upbitPriceResponses.get(0).getCode());
                    if (mLineViewModel == null)
                        initLineViewModel();
                    mLineViewModel.sendMsgAndImg(upbitPriceResponses.get(0).getCode() + " => 원화 상장 예정\n\n",
                            upbitPriceResponses.get(0).getLogoImgUrl());


                    List<UpbitPriceResponse> result = QcPreferences.getInstance().getList(Define.PRE_UPBIT_KRW_LIST, UpbitPriceResponse.class);
                    QcLog.e("getKrwList observe 11 == " + result.size() + " , " + result.toString());
                    if (result == null || result.isEmpty())
                        result = new ArrayList<UpbitPriceResponse>();
                    result.add(upbitPriceResponses.get(0));
                    QcLog.e("getKrwList observe 22== " + result.size() + " , " + result.toString());
                    QcPreferences.getInstance().putList(Define.PRE_UPBIT_KRW_LIST, result);

                }
                stopSelf();
            }
        });

        update();

        return super.onStartCommand(intent, flags, startId);
//        return START_STICKY;
    }

    private void update() {
        if (mUpbitKrwViewModel != null) {
            QcLog.e("update ==");
            // 원화상자예정
//        https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-GTO&count=2&to=2018-04-20%2011:42:00
//            mUpbitKrwViewModel.loadKrwList("GTO", "1", "2018-04-07 11:42:00", null);

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
            String getTime = simpleDate.format(System.currentTimeMillis());
            QcToast.getInstance().show("Update Start == " + getTime, false);

            String[] coinSymbol = getResources().getStringArray(R.array.array_coin_symbol);

            coinSymbolList = new ArrayList<>(Arrays.asList(coinSymbol));
            if (coinSymbolList == null) {
                stopSelf();
                return;
            }

            if (coinSymbolList.size() == 0) {
                stopSelf();
                return;
            }

            List<UpbitPriceResponse> result = QcPreferences.getInstance().getList(Define.PRE_UPBIT_KRW_LIST, UpbitPriceResponse.class);
            QcLog.e("getKrwList observe 11 == " + result.size() + " , " + result.toString());
            if (result == null || result.isEmpty())
                result = new ArrayList<UpbitPriceResponse>();

            if (result != null && result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    for (int i = 0; i < coinSymbolList.size(); i++) {
                        if (!coinSymbolList.get(i).equals(result.get(j).getCode())) {
                            coinSymbolList.remove(i);
                        }
                    }
                }
            }


            for (int i = 0; i < coinSymbolList.size(); i++) {
                mUpbitKrwViewModel.loadKrwList(coinSymbolList.get(i), "1", getTime, null);
            }

            startUpbitKrw();
        }
    }


    public void initLineViewModel() {
        mLineViewModel = new LineViewModel(qApp);
        mLineViewModel.setViewModel(LineModel.getInstance());
    }

    public void startUpbitKrw() {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = HANDLE_UPBIT;
        mServiceHandler.sendMessage(msg);
    }

//    public void sendSMS(String coinSymbol) {
//        String smsNum = "010-3109-3050";
//        String smsText = coinSymbol + " 원화 상장 시그널 포착!";
//
//        if (smsNum.length() > 0 && smsText.length() > 0) {
//            sendSMS(smsNum, smsText);
//        }
//    }
//
//    private void sendSMS(String phoneNumber, String message) {
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, null, null);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.e("onDestroy ==");
        startService = false;
        qApp.getIsUpbitService().postValue(false);
        QcToast.getInstance().show("Service Destroy ! ", false);
    }
}
