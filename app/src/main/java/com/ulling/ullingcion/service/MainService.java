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
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.util.QcUtil;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;
import com.ulling.ullingcion.model.CryptoWatchModel;
import com.ulling.ullingcion.model.LineModel;
import com.ulling.ullingcion.model.MainModel;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.util.Utils;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;
import com.ulling.ullingcion.viewmodel.LineViewModel;
import com.ulling.ullingcion.viewmodel.MainViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainService extends LifecycleService {
    public String TAG = getClass().getSimpleName();
    private final int THREAD_PRIORITY_BACKGROUND = 1000;

    private final int HANDLE_USD_TO_KRW = 10;
    private final int HANDLE_UPBIT = 20;
    private final int HANDLE_CRYPTOWATCH = 30;
    private final int HANDLE_CRYPTOWATCH_CANDLES = 40;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private QUllingApplication qApp;
    private boolean startService = false;

    private MainViewModel mMainModel;
    private CryptoWatchViewModel mCryptoWatchViewModel;
    private UpbitKrwViewModel mUpbitKrwViewModel;
    private LineViewModel mLineViewModel;

    private boolean isUsdToKrw = false;
    private boolean isCryptoWatch = false;
    private boolean isCryptoWatchCandles = false;
    private boolean isUpdateUpbit = false;

    private SimpleDateFormat simpleDate;
    private int lastSummary = 0;

    private UpbitUsdToKrwResponse mUpbitUsdToKrwResponse;
    private UpbitPriceResponse mUpbitPriceResponse;
    private CryptowatSummary mCryptowatSummary;

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
                            // 10분 마다
                            Thread.sleep(20 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateUsdToKrw();
                    }

                } else if (msg.arg1 == HANDLE_UPBIT) {
                    if (isUpdateUpbit) {
                        try {
                            // 1분 마다
                            Thread.sleep(20 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateUpbitBtcPrice();
                    }

                } else if (msg.arg1 == HANDLE_CRYPTOWATCH) {
                    if (isCryptoWatch) {
                        try {
                            // 1분 마다
                            Thread.sleep(20 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoSummary();
                    }

                } else if (msg.arg1 == HANDLE_CRYPTOWATCH_CANDLES) {
                    if (isCryptoWatchCandles) {
                        try {
                            // 20분 마다
                            Thread.sleep(20 * 60 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoCandles();
                    }

                }

                if (!isUsdToKrw && !isCryptoWatch && !isCryptoWatchCandles && !isUpdateUpbit)
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

        mUpbitKrwViewModel = new UpbitKrwViewModel(qApp);
        mUpbitKrwViewModel.setViewModel(UpbitKrwModel.getInstance());

        mLineViewModel = new LineViewModel(qApp);
        mLineViewModel.setViewModel(LineModel.getInstance());

        mUpbitUsdToKrwResponse = QcPreferences.getInstance().get(Define.PRE_USD_TO_KRW, UpbitUsdToKrwResponse.class);

        if (mMainModel != null)
            mMainModel.getUsdToKrw().observe(this, new Observer<List<UpbitUsdToKrwResponse>>() {
                @Override
                public void onChanged(@Nullable List<UpbitUsdToKrwResponse> upbitUsdToKrwResponses) {
                    QcLog.e("observe getUsdToKrw == ");
                    if (upbitUsdToKrwResponses != null && upbitUsdToKrwResponses.size() > 0) {
                        mUpbitUsdToKrwResponse = upbitUsdToKrwResponses.get(0);
                        QcPreferences.getInstance().put(Define.PRE_USD_TO_KRW, upbitUsdToKrwResponses);
                        QcLog.e("getUsdToKrw === " + mUpbitUsdToKrwResponse.toString());
                        getPremiumBtcPrice();
                    }
                }
            });

        if (mCryptoWatchViewModel != null) {
            mCryptoWatchViewModel.getSummary().observe(this, new Observer<CryptowatSummary>() {
                @Override
                public void onChanged(@Nullable CryptowatSummary cryptowatSummary) {
                    QcLog.e("observe getSummary == ");
                    if (cryptowatSummary != null && cryptowatSummary.getResult() != null) {
                        mCryptowatSummary = cryptowatSummary;
                        getPremiumBtcPrice();

                        double lastPrice = cryptowatSummary.getResult().getPrice().getLast();
//                        double lastSummary_ = Utils.getRound(lastPrice, Utils.NUMBER_HUNDRED);
                        BigDecimal lastSummary_ = QcUtil.GetDoubleDivide(lastPrice, 50);
                        QcLog.e("lastSummary_ == " + lastSummary_.intValue() + " , " + lastSummary+ " , " + lastPrice);
//                        if (Utils.getRound(lastPrice, Utils.NUMBER_HUNDRED) != lastSummary) {
                        if (lastSummary_.intValue()!= lastSummary) {
                            lastSummary = lastSummary_.intValue();
//                            100 단위마다 알림
                            mLineViewModel.sendMsg("Bitfinex 가격 : " + cryptowatSummary.getResult().getPrice().getLast() + "달러");
                        }
                    }
                }
            });

//            mCryptoWatchViewModel.getCandlesStick().observe(this, new Observer<CryptoWatchCandles>() {
//                @Override
//                public void onChanged(@Nullable CryptoWatchCandles cryptoWatchCandles) {
//                    QcLog.e("observe getCandles == " + cryptoWatchCandles.toString());
//
////                    if (cryptoWatchCandles != null && cryptoWatchCandles.getResult() != null) {
////                        if (cryptoWatchCandles.getResult().getCandles_1D() != null) {
////                            for (int i = 0; i < cryptoWatchCandles.getResult().getCandles_1D().size(); i++) {
////                                List<String> list = cryptoWatchCandles.getResult().getCandles_1D().get(i);
////                                long closeTime = Long.parseLong(list.get(0)) * 1000;
////
////                                Double OpenPrice = Double.parseDouble(list.get(1));
////                                Double HighPrice = Double.parseDouble(list.get(2));
////                                Double LowPrice = Double.parseDouble(list.get(3));
////                                Double ClosePrice = Double.parseDouble(list.get(4));
////                                Double Volume = Double.parseDouble(list.get(5));
////                                Date date = new Date(closeTime);
////
////                                String formattedDate = simpleDate.format(date);
////                            }
////                        }
////                    }
//                }
//            });
        }

        if (mUpbitKrwViewModel != null)
            mUpbitKrwViewModel.getCoinPrice().observe(this, new Observer<UpbitPriceResponse>() {
                @Override
                public void onChanged(@Nullable UpbitPriceResponse upbitPriceResponse) {
                    if (upbitPriceResponse != null) {
                        mUpbitPriceResponse = upbitPriceResponse;
                        getPremiumBtcPrice();
                    }

                }
            });

        isUsdToKrw = true;
        isUpdateUpbit = true;
        isCryptoWatch = true;
        isCryptoWatchCandles = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        QcLog.e("onStartCommand ==");
        QcToast.getInstance().show(TAG + " Start ! ", false);

        updateUsdToKrw();
        updateUpbitBtcPrice();
        updateCryptoSummary();
//        updateCryptoCandles();

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

    private void updateUpbitBtcPrice() {
        if (mUpbitKrwViewModel != null) {
            QcLog.e("updateUpbitBtcPrice ==");

            String getTime = simpleDate.format(System.currentTimeMillis());

            String coinSymbol = getResources().getString(R.string.coin_symbol_btc);

            mUpbitKrwViewModel.loadCoinPrice(coinSymbol, "1", getTime, null);

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
            Date after = QcUtil.GetDate(2018, 1, 1, 0, 0, 0);
            mCryptoWatchViewModel.loadCandlesStick(QcUtil.GetUnixTime(after.getTime()), Define.VALUE_CRYPTOWAT_1D);

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_CRYPTOWATCH_CANDLES;
            mServiceHandler.sendMessage(msg);
        }
    }


    private void getPremiumBtcPrice() {
        QcLog.e("getPremiumBtcPrice === ");
        if (mUpbitUsdToKrwResponse != null && mUpbitPriceResponse != null && mCryptowatSummary != null) {

            double upbitBtcPrice = mUpbitPriceResponse.getTradePrice();
            double cryptoBtcUsd = QcUtil.GetDoubleMultiply(mUpbitUsdToKrwResponse.getBasePrice(), mCryptowatSummary.getResult().getPrice().getLast()).doubleValue();

            BigDecimal price = QcUtil.GetDoubleDivide(upbitBtcPrice, cryptoBtcUsd, 4);
            BigDecimal price_ = QcUtil.GetDoubleSubtract(price.doubleValue(), 1.0);
            BigDecimal premiumPercent = QcUtil.GetDoubleMultiply(price_.doubleValue(), 100.0);
            BigDecimal premium = QcUtil.GetDoubleSubtract(upbitBtcPrice, cryptoBtcUsd);

            if (premium.intValue() <= 0) {
                //역프일때만
                String text =
                        mUpbitUsdToKrwResponse.getDate() + "\n"
                                + simpleDate.format(mUpbitPriceResponse.getTimestamp()) + "\n"
                                + "환율 1달러 : " + QcUtil.toNumFormat(mUpbitUsdToKrwResponse.getBasePrice()) + " 원" + "\n"
                                + "Bitfinex 가격 : " + QcUtil.toNumFormat(mCryptowatSummary.getResult().getPrice().getLast()) + "달러\n"
                                + "(원화 : " + QcUtil.toNumFormat(cryptoBtcUsd) + " 원)" + "\n"
                                + "업비트 가격 : " + QcUtil.toNumFormat(upbitBtcPrice) + " 원 \n"
                                + "프리미엄 : " + QcUtil.toNumFormat(premium.intValue()) + " ("
                                + QcUtil.toNumFormat(premiumPercent.doubleValue()) + "%)"
                                + "https://luka7.net/";

                mLineViewModel.sendMsg(text);
            }
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
        isUpdateUpbit = false;

        startService = false;
        qApp.getIsUpbitService().postValue(false);
        QcToast.getInstance().show(TAG + " Destroy ! ", false);
    }
}
