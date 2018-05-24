package com.ulling.ullingcion.service;

import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.util.QcUtil;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.model.CryptoWatchModel;
import com.ulling.ullingcion.model.LineModel;
import com.ulling.ullingcion.model.MainModel;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;
import com.ulling.ullingcion.viewmodel.LineViewModel;
import com.ulling.ullingcion.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainService extends LifecycleService {
    public String TAG = getClass().getSimpleName();
    private final int THREAD_PRIORITY_BACKGROUND = 1000;
    private final int HANDLE_USD_TO_KRW = 10;
    private final int HANDLE_CRYPTOWATCH = 20;
    private final int HANDLE_CRYPTOWATCH_CANDLES = 30;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private QUllingApplication qApp;
    private boolean startService = false;

    private MainViewModel mMainModel;
    private CryptoWatchViewModel mCryptoWatchViewModel;
    private LineViewModel mLineViewModel;


    private boolean isUsdToKrw = false;
    private boolean isCryptoWatch = false;
    private boolean isCryptoWatchCandles = false;

    private SimpleDateFormat simpleDate;
    private int lastSummary = 0;

    public MainService() {
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            QcLog.e("handleMessage == " + msg.arg1);
            if (startService) {
                if (msg.arg1 == HANDLE_USD_TO_KRW) {
                    if (isUsdToKrw) {
                        try {
                            // 30분 마다
                            Thread.sleep(30 * 60 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateUsdToKrw();
                    }
                } else if (msg.arg1 == HANDLE_CRYPTOWATCH) {
                    if (isCryptoWatch) {
                        try {
                            // 1분 마다
                            Thread.sleep(1 * 60 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoSummary();
                    }


                } else if (msg.arg1 == HANDLE_CRYPTOWATCH_CANDLES) {
                    if (isCryptoWatchCandles) {
                        try {
                            // 1분 마다
                            Thread.sleep(1 * 60 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoCandles();
                    }
                }

                if (!isUsdToKrw && !isCryptoWatch && !isCryptoWatchCandles)
                    stopSelf();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        QcLog.e("onCreate ==");

        qApp = QUllingApplication.getInstance();
        startService = true;
        qApp.getIsUpbitService().postValue(true);

        simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+9"));

        HandlerThread thread = new HandlerThread("ServiceStartArguments", THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        mMainModel = new MainViewModel(qApp);
        mMainModel.setViewModel(MainModel.getInstance());

        mCryptoWatchViewModel = new CryptoWatchViewModel(qApp);
        mCryptoWatchViewModel.setViewModel(CryptoWatchModel.getInstance());

        mLineViewModel = new LineViewModel(qApp);
        mLineViewModel.setViewModel(LineModel.getInstance());


        if (mCryptoWatchViewModel != null) {
            mCryptoWatchViewModel.getSummary().observe(this, new Observer<CryptowatSummary>() {
                @Override
                public void onChanged(@Nullable CryptowatSummary cryptowatSummary) {
                    QcLog.e("observe getSummary == " + cryptowatSummary.toString());

                    if (cryptowatSummary != null && cryptowatSummary.getResult() != null) {
//                        Double ladtPrice = Double.parseDouble(cryptowatSummary.getResult().getPrice().getLast());
                        double ladtPrice = cryptowatSummary.getResult().getPrice().getLast();

                        if (ladtPrice <= 6300 && lastSummary != 6300) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 6300;
                            return;
                        }
                        if (ladtPrice <= 6400 && lastSummary != 6400) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 6400;
                            return;
                        }
                        if (ladtPrice <= 6500 && lastSummary != 6500) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 6500;
                            return;
                        }
                        if (ladtPrice <= 6600 && lastSummary != 6600) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 6600;
                            return;
                        }
                        if (ladtPrice <= 6700 && lastSummary != 6700) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 6700;
                            return;
                        }
                        if (ladtPrice <= 6800 && lastSummary != 6800) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 6800;
                            return;
                        }
                        if (ladtPrice <= 6900 && lastSummary != 6900) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 6900;
                            return;
                        }
                        if (ladtPrice <= 7000 && lastSummary != 7000) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7000;
                            return;
                        }
                        if (ladtPrice <= 7100 && lastSummary != 7100) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7100;
                            return;
                        }
                        if (ladtPrice <= 7300 && lastSummary != 7300) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7300;
                            return;
                        }
                        if (ladtPrice <= 7400 && lastSummary != 7400) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7400;
                            return;
                        }
                        if (ladtPrice <= 7500 && lastSummary != 7500) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7500;
                            return;
                        }
                        if (ladtPrice <= 7600 && lastSummary != 7600) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7600;
                            return;
                        }
                        if (ladtPrice <= 7700 && lastSummary != 7700) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7700;
                            return;
                        }
                        if (ladtPrice <= 7800 && lastSummary != 7800) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7800;
                            return;
                        }
                        if (ladtPrice <= 7900 && lastSummary != 7900) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 7900;
                            return;
                        }
                        if (ladtPrice <= 8000 && lastSummary != 8000) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 8000;
                            return;
                        }
                        if (ladtPrice <= 8100 && lastSummary != 8100) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 8100;
                            return;
                        }
                        if (ladtPrice <= 8200 && lastSummary != 8200) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            lastSummary = 8200;
                            return;
                        }
                    }
                }
            });

            mCryptoWatchViewModel.getCandlesStick().observe(this, new Observer<CryptoWatchCandles>() {
                @Override
                public void onChanged(@Nullable CryptoWatchCandles cryptoWatchCandles) {
                    QcLog.e("observe getCandles == " + cryptoWatchCandles.toString());

                    if (cryptoWatchCandles != null && cryptoWatchCandles.getResult() != null) {
                        if (cryptoWatchCandles.getResult().getCandles_1D() != null) {
                            for (int i = 0; i < cryptoWatchCandles.getResult().getCandles_1D().size(); i++) {
                                List<String> list = cryptoWatchCandles.getResult().getCandles_1D().get(i);
                                long closeTime = Long.parseLong(list.get(0)) * 1000;

                                Double OpenPrice = Double.parseDouble(list.get(1));
                                Double HighPrice = Double.parseDouble(list.get(2));
                                Double LowPrice = Double.parseDouble(list.get(3));
                                Double ClosePrice = Double.parseDouble(list.get(4));
                                Double Volume = Double.parseDouble(list.get(5));
                                Date date = new Date(closeTime);

                                String formattedDate = simpleDate.format(date);
                                QcLog.e("formattedDate == " + formattedDate + " == OpenPrice " + OpenPrice + " , " + HighPrice + " , " + LowPrice + " , " + ClosePrice + " , " + Volume);
                            }
                        }

                    }
                }
            });
        }

        isUsdToKrw = true;
        isCryptoWatch = true;
        isCryptoWatchCandles = true;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        QcLog.e("onStartCommand ==");
        QcToast.getInstance().show(TAG + " Start ! ", false);

        updateUsdToKrw();
        updateCryptoSummary();
        updateCryptoCandles();

        mLineViewModel.sendMsg("Coin Service START !");

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateUsdToKrw() {
        if (mMainModel != null) {
            QcLog.e("updateUsdToKrw ==");
            mMainModel.loadUsdToKrw();

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_USD_TO_KRW;
            mServiceHandler.sendMessage(msg);
        }
    }

    private void updateCryptoSummary() {
        if (mCryptoWatchViewModel != null) {
            mCryptoWatchViewModel.loadSummary();

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_CRYPTOWATCH;
            mServiceHandler.sendMessage(msg);
        }
    }

    private void updateCryptoCandles() {
        if (mCryptoWatchViewModel != null) {
            Date after = QcUtil.GetDate(2017, 1, 1, 0, 0, 0);
            mCryptoWatchViewModel.loadCandlesStick(QcUtil.GetUnixTime(after.getTime()), Define.VALUE_CRYPTOWAT_1D);

//            Message msg = mServiceHandler.obtainMessage();
//            msg.arg1 = HANDLE_CRYPTOWATCH_CANDLES;
//            mServiceHandler.sendMessage(msg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.e("onDestroy ==");
        mLineViewModel.sendMsg(TAG + " STOP !");

        isUsdToKrw = false;
        isCryptoWatch = false;
        isCryptoWatchCandles = false;

        startService = false;
        qApp.getIsUpbitService().postValue(false);
        QcToast.getInstance().show(TAG + " Destroy ! ", false);
    }
}