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
import com.ulling.lib.core.util.QcToast;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UpbitService extends LifecycleService {
    private final int THREAD_PRIORITY_BACKGROUND = 1000;
    private final int HANDLE_UPBIT = 10;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private QUllingApplication qApp;
    private boolean startService = false;
    private UpbitKrwViewModel mUpbitKrwViewModel;
    private String[] coinSymbol;

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
                    Thread.sleep(30000);
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
                sendSMS(upbitPriceResponses.get(0).getCode());
                stopSelf();
            }
        });

        update();

        return super.onStartCommand(intent, flags, startId);
//        return START_STICKY;
    }

    private void update() {
        if (mUpbitKrwViewModel != null) {
            // 원화상자예정
//        https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-GTO&count=2&to=2018-04-20%2011:42:00
//            mUpbitKrwViewModel.loadKrwList("GTO", "1", "2018-04-07 11:42:00");

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
            String getTime = simpleDate.format(System.currentTimeMillis());
            QcToast.getInstance().show("Update Start == " + getTime, false);

            coinSymbol = getResources().getStringArray(R.array.array_coin_symbol);
            if (coinSymbol != null)
                for (int i = 0; i < coinSymbol.length; i++)
                    mUpbitKrwViewModel.loadKrwList(coinSymbol[i], "1", getTime);


            startUpbitKrw();
        }
    }


    public void startUpbitKrw() {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = HANDLE_UPBIT;
        mServiceHandler.sendMessage(msg);
    }

    public void sendSMS(String coinSymbol) {
        String smsNum = "010-3109-3050";
        String smsText = coinSymbol + " 원화 상장 시그널 포착!";

        if (smsNum.length() > 0 && smsText.length() > 0) {
            sendSMS(smsNum, smsText);
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.e("onDestroy ==");
        startService = false;
        qApp.getIsUpbitService().postValue(false);
        QcToast.getInstance().show("Service Destroy ! ", false);
    }
}
