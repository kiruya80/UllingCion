package com.ulling.ullingcion.service;

import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.LiveData;
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
import com.ulling.lib.core.util.QcUtil;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.CryptoWatchModel;
import com.ulling.ullingcion.model.LineModel;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;
import com.ulling.ullingcion.viewmodel.LineViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class UpbitService extends LifecycleService {
    private final int THREAD_PRIORITY_BACKGROUND = 1000;
    private final int HANDLE_UPBIT = 10;
    private final int HANDLE_CRYPTOWATCH = 20;
    private final int HANDLE_CRYPTOWATCH_CANDLES = 30;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private QUllingApplication qApp;
    private boolean startService = false;

    private UpbitKrwViewModel mUpbitKrwViewModel;
    private CryptoWatchViewModel mCryptoWatchViewModel;
    private LineViewModel mLineViewModel;


    private ArrayList<String> coinSymbolList;

    private boolean isUpdateUpbit = false;
    private boolean isCryptoWatch = false;
    private boolean isCryptoWatchCandles = false;

    private SimpleDateFormat simpleDate;


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
                if (msg.arg1 == HANDLE_UPBIT) {
                    if (isUpdateUpbit) {
                        try {
                            // 10분 마다
                            Thread.sleep(3 * 60 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateUpbitKrw();
                    }

                } else if (msg.arg1 == HANDLE_CRYPTOWATCH) {
                    if (isCryptoWatch) {
                        try {
                            // 10분 마다
                            Thread.sleep(1 * 60 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoSummary();
                    }


                } else if (msg.arg1 == HANDLE_CRYPTOWATCH_CANDLES) {
                    if (isCryptoWatchCandles) {
                        try {
                            // 10분 마다
                            Thread.sleep(1 * 60 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoCandles();
                    }
                }

                if (!isUpdateUpbit && !isCryptoWatch && !isCryptoWatchCandles)
                    stopSelf();
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

        simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+9"));

        HandlerThread thread = new HandlerThread("ServiceStartArguments", THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        mUpbitKrwViewModel = new UpbitKrwViewModel(qApp);
        mUpbitKrwViewModel.setViewModel(UpbitKrwModel.getInstance());


        mCryptoWatchViewModel = new CryptoWatchViewModel(qApp);
        mCryptoWatchViewModel.setViewModel(CryptoWatchModel.getInstance());

        mLineViewModel = new LineViewModel(qApp);
        mLineViewModel.setViewModel(LineModel.getInstance());

        mUpbitKrwViewModel.getKrwList().observe(this, new Observer<List<UpbitPriceResponse>>() {
            @Override
            public void onChanged(@Nullable List<UpbitPriceResponse> upbitPriceResponses) {
                QcLog.e("getKrwList observe === ");
                // 원화 상장 예정
                QcToast.getInstance().show("원화 상장 예정 ! ", false);
                if (upbitPriceResponses != null && upbitPriceResponses.size() > 0) {
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
            }
        });

        if (mCryptoWatchViewModel != null) {
            mCryptoWatchViewModel.getSummary().observe(this, new Observer<CryptowatSummary>() {
                @Override
                public void onChanged(@Nullable CryptowatSummary cryptowatSummary) {
                    QcLog.e("observe getSummary == " + cryptowatSummary.toString());

                    if (cryptowatSummary != null && cryptowatSummary.getResult() != null) {
                        Double ladtPrice = Double.parseDouble(cryptowatSummary.getResult().getPrice().getLast());

                        if (ladtPrice <= 7200) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 7300) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 7400) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 7500) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 7600) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 7700) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 7800) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 7900) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                        if (ladtPrice <= 8200) {
                            mLineViewModel.sendMsg(cryptowatSummary.toString());
                            return;
                        }
                    }
                }
            });

            mCryptoWatchViewModel.getCandles().observe(this, new Observer<CryptoWatchCandles>() {
                @Override
                public void onChanged(@Nullable CryptoWatchCandles cryptoWatchCandles) {
                    QcLog.e("observe getCandles == " + cryptoWatchCandles.toString());

                    if (cryptoWatchCandles != null && cryptoWatchCandles.getResult() != null) {
                        if (cryptoWatchCandles.getResult().getCandles_1D() != null) {
                            for (int i = 0; i < cryptoWatchCandles.getResult().getCandles_1D().size(); i++) {
                                List<String> list = cryptoWatchCandles.getResult().getCandles_1D().get(i);
                                long coinTime = Long.parseLong(list.get(0)) * 1000;

                                Double OpenPrice = Double.parseDouble(list.get(1));
                                Double HighPrice = Double.parseDouble(list.get(2));
                                Double LowPrice = Double.parseDouble(list.get(3));
                                Double ClosePrice = Double.parseDouble(list.get(4));
                                Double Volume = Double.parseDouble(list.get(5));
                                Date date = new Date(coinTime);

                                String formattedDate = simpleDate.format(date);
                                QcLog.e("formattedDate == " + formattedDate + " == OpenPrice " + OpenPrice + " , " + HighPrice + " , " + LowPrice + " , " + ClosePrice + " , " + Volume);
                            }
                        }

                        QcLog.e("observe ================ ");
                        if (cryptoWatchCandles.getResult().getCandles_2D() != null) {
                            QcLog.e("observe getCandles_2D == " + cryptoWatchCandles.getResult().getCandles_2D().toString());
                        }
                    }
                }
            });
        }

        isUpdateUpbit = true;
        isCryptoWatch = true;
        isCryptoWatchCandles = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        QcLog.e("onStartCommand ==");
        QcToast.getInstance().show("Service Start ! ", false);

        updateUpbitKrw();
        updateCryptoSummary();
        updateCryptoCandles();

        mLineViewModel.sendMsg("Coin Service START !");

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateUpbitKrw() {
        if (mUpbitKrwViewModel != null) {
            QcLog.e("UpdateUpbitKrw ==");

            // 원화상자예정
//        https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-GTO&count=2&to=2018-04-20%2011:42:00
//            mUpbitKrwViewModel.loadKrwList("GTO", "1", "2018-04-07 11:42:00", null);

            String getTime = simpleDate.format(System.currentTimeMillis());
            QcToast.getInstance().show("Update Start == " + getTime, false);

            String[] coinSymbol = getResources().getStringArray(R.array.array_coin_symbol);

            coinSymbolList = new ArrayList<>(Arrays.asList(coinSymbol));
            if (coinSymbolList == null) {
                isUpdateUpbit = false;
                return;
            }

            if (coinSymbolList.size() == 0) {
                isUpdateUpbit = false;
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

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_UPBIT;
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

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_CRYPTOWATCH_CANDLES;
            mServiceHandler.sendMessage(msg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.e("onDestroy ==");
        mLineViewModel.sendMsg("Coin Service STOP !");

        isUpdateUpbit = false;
        isCryptoWatch = false;
        isCryptoWatchCandles = false;

        startService = false;
        qApp.getIsUpbitService().postValue(false);
        QcToast.getInstance().show("Service Destroy ! ", false);
    }
}
