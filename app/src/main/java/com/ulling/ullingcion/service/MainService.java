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
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;
import com.ulling.ullingcion.viewmodel.LineViewModel;
import com.ulling.ullingcion.viewmodel.MainViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainService extends LifecycleService {
    public String TAG = getClass().getSimpleName();
    private final int THREAD_PRIORITY_BACKGROUND = 1000;

    private final int HANDLE_UPBIT_BTC = 10;
    private final int HANDLE_UPBIT_KRW_COIN = 20;
    private final int HANDLE_CRYPTOWATCH = 30;
    private final int HANDLE_CRYPTOWATCH_CANDLES = 40;
    private final int HANDLE_USD_TO_KRW = 50;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private QUllingApplication qApp;
    private boolean startService = false;

    private MainViewModel mMainModel;
    private CryptoWatchViewModel mCryptoWatchViewModel;
    private UpbitKrwViewModel mUpbitKrwViewModel;
    private LineViewModel mLineViewModel;

    private boolean isUsdToKrw = false;
    private boolean isUpdateUpbitBtc = false;
    private boolean isUpdateUpbitKrwCoin = false;
    private boolean isCryptoWatch = false;
    private boolean isCryptoWatchCandles = false;

    private SimpleDateFormat simpleDate;
    private int lastSummary = 0;
    private double lastSummary_100 = 0;

    private UpbitUsdToKrwResponse mUpbitUsdToKrwResponse;
    private UpbitPriceResponse mUpbitPriceResponse;
    private CryptowatSummary mCryptowatSummary;

    private List<Integer> btcPriceList;
    private ArrayList<String> coinSymbolList;

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
                if (msg.arg1 == HANDLE_UPBIT_BTC) {
                    // 업비트 btc 원화 가격 가져오기
                    if (isUpdateUpbitBtc) {
                        try {
                            // 1분 마다
                            Thread.sleep(30 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateUpbitBtcPrice();
                    }

                } else if (msg.arg1 == HANDLE_UPBIT_KRW_COIN) {
                    // 원화상장 예정 체크
                    if (isUpdateUpbitKrwCoin) {
                        try {
                            // 1분 마다
                            Thread.sleep(30 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateUpbitKrwCoin();
                    }

                } else if (msg.arg1 == HANDLE_CRYPTOWATCH) {
                    // 크립토 현재 가격
                    if (isCryptoWatch) {
                        try {
                            // 1분 마다
                            Thread.sleep(30 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoSummary();
                    }

                } else if (msg.arg1 == HANDLE_CRYPTOWATCH_CANDLES) {
                    // 크립토 캔들 리스트 가져오기
                    if (isCryptoWatchCandles) {
                        try {
                            // 20분 마다
                            Thread.sleep(30 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateCryptoCandles();
                    }

                } else if (msg.arg1 == HANDLE_USD_TO_KRW) {
                    //환율 가져오기
                    if (isUsdToKrw) {
                        try {
                            // 10분 마다
                            Thread.sleep(30 * 1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        updateUsdToKrw();
                    }
                }

                if (!isUsdToKrw && !isCryptoWatch && !isCryptoWatchCandles && !isUpdateUpbitBtc && !isUpdateUpbitKrwCoin)
                    stopSelf();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        qApp = QUllingApplication.getInstance();
        startService = true;
        qApp.getIsUpbitService().postValue(true);
        btcPriceList = QcPreferences.getInstance().getList(Define.PRE_BTC_PRICE, Integer.class);

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
                    if (upbitUsdToKrwResponses != null && upbitUsdToKrwResponses.size() > 0) {
                        mUpbitUsdToKrwResponse = upbitUsdToKrwResponses.get(0);
                        QcPreferences.getInstance().put(Define.PRE_USD_TO_KRW, upbitUsdToKrwResponses);
                        getPremiumBtcPrice();
                    }
                }
            });

        if (mCryptoWatchViewModel != null) {
            mCryptoWatchViewModel.getSummary().observe(this, new Observer<CryptowatSummary>() {
                @Override
                public void onChanged(@Nullable CryptowatSummary cryptowatSummary) {
                    if (cryptowatSummary != null && cryptowatSummary.getResult() != null) {
                        mCryptowatSummary = cryptowatSummary;
                        getPremiumBtcPrice();

                        double lastPrice = cryptowatSummary.getResult().getPrice().getLast();

                        // 사용자가 정한 가격알림
                        if (btcPriceList != null && btcPriceList.size() > 0) {
                            for (int i = 0; i < btcPriceList.size(); i++) {
                                if (btcPriceList.get(i) == lastPrice) {
                                    mLineViewModel.sendMsg("Bitfinex 가격 : " + cryptowatSummary.getResult().getPrice().getLast() + "달러");
                                    return;
                                }
                            }
                        }

                        BigDecimal lastSummary_50 = QcUtil.GetDoubleRemainder(lastPrice, 50);
                        if (lastSummary_50.longValue() == 0) {
                            //  50단위로 알림
                            lastSummary = lastSummary_50.intValue();
                            mLineViewModel.sendMsg("Bitfinex 가격 : " + lastPrice + "달러");

                        } else {
                        }
                    }
                }
            });

//            mCryptoWatchViewModel.getCandlesStick().observe(this, new Observer<CryptoWatchCandles>() {
//                @Override
//                public void onChanged(@Nullable CryptoWatchCandles cryptoWatchCandles) {
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

        if (mUpbitKrwViewModel != null) {
            // 업비트 btc 원화가격 가져오기 프리미엄 계산
            mUpbitKrwViewModel.getCoinPrice().observe(this, new Observer<UpbitPriceResponse>() {
                @Override
                public void onChanged(@Nullable UpbitPriceResponse upbitPriceResponse) {
                    if (upbitPriceResponse != null) {
                        mUpbitPriceResponse = upbitPriceResponse;
                        getPremiumBtcPrice();
                    }

                }
            });

            mUpbitKrwViewModel.getKrwList().observe(this, new Observer<List<UpbitPriceResponse>>() {
                @Override
                public void onChanged(@Nullable List<UpbitPriceResponse> upbitPriceResponses) {
                    // 원화 상장 예정
                    QcToast.getInstance().show("원화 상장 예정 ! ", false);
                    if (upbitPriceResponses != null && upbitPriceResponses.size() > 0) {
                        mLineViewModel.sendMsgAndImg(upbitPriceResponses.get(0).getCode() + " => 원화 상장 예정\n\n",
                                upbitPriceResponses.get(0).getLogoImgUrl());


                        List<UpbitPriceResponse> result = QcPreferences.getInstance().getList(Define.PRE_UPBIT_KRW_LIST, UpbitPriceResponse.class);
                        if (result == null || result.isEmpty())
                            result = new ArrayList<UpbitPriceResponse>();
                        result.add(upbitPriceResponses.get(0));
                        QcPreferences.getInstance().putList(Define.PRE_UPBIT_KRW_LIST, result);

                    }
                }
            });
        }

        isUsdToKrw = true;
        isUpdateUpbitBtc = true;
        isCryptoWatch = true;
        isCryptoWatchCandles = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        QcToast.getInstance().show(TAG + " Start ! ", false);

        updateUsdToKrw();

        mLineViewModel.sendMsg("Coin Service START !");

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateUsdToKrw() {
        if (mMainModel != null) {
            mMainModel.loadUsdToKrw();

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_UPBIT_BTC;
            mServiceHandler.sendMessage(msg);
        }
    }

    private void updateUpbitBtcPrice() {
        if (mUpbitKrwViewModel != null) {
            String getTime = simpleDate.format(System.currentTimeMillis());

            String coinSymbol = getResources().getString(R.string.coin_symbol_btc);

            mUpbitKrwViewModel.loadCoinPrice(coinSymbol, "1", getTime, null);

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_UPBIT_KRW_COIN;
            mServiceHandler.sendMessage(msg);
        }
    }


    private void updateUpbitKrwCoin() {
        if (mUpbitKrwViewModel != null && isUpdateUpbitKrwCoin) {

            // 원화상자예정
//        https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-GTO&count=2&to=2018-04-20%2011:42:00
//            mUpbitKrwViewModel.loadKrwList("GTO", "1", "2018-04-07 11:42:00", null);

            String getTime = simpleDate.format(System.currentTimeMillis());
            QcToast.getInstance().show("Update Start == " + getTime, false);

            String[] coinSymbol = getResources().getStringArray(R.array.array_coin_symbol);

            coinSymbolList = new ArrayList<>(Arrays.asList(coinSymbol));
            if (coinSymbolList == null) {
                isUpdateUpbitKrwCoin = false;
            }

            if (coinSymbolList.size() == 0) {
                isUpdateUpbitKrwCoin = false;
            }

            if (isUpdateUpbitKrwCoin) {
                List<UpbitPriceResponse> result = QcPreferences.getInstance().getList(Define.PRE_UPBIT_KRW_LIST, UpbitPriceResponse.class);
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
            }
        }

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = HANDLE_CRYPTOWATCH;
        mServiceHandler.sendMessage(msg);
    }

    private void updateCryptoSummary() {
        if (mCryptoWatchViewModel != null) {
            mCryptoWatchViewModel.loadSummary();

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_CRYPTOWATCH_CANDLES;
            mServiceHandler.sendMessage(msg);
        }
    }

    private void updateCryptoCandles() {
        if (mCryptoWatchViewModel != null) {
            Date after = QcUtil.GetDate(2018, 1, 1, 0, 0, 0);
            mCryptoWatchViewModel.loadCandlesStick(QcUtil.GetUnixTime(after.getTime()), Define.VALUE_CRYPTOWAT_1D);

            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = HANDLE_USD_TO_KRW;
            mServiceHandler.sendMessage(msg);
        }
    }

    /**
     * 프리미엄 계산
     */
    private void getPremiumBtcPrice() {
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
        isUpdateUpbitBtc = false;
        isUpdateUpbitKrwCoin = false;
        isCryptoWatch = false;
        isCryptoWatchCandles = false;

        startService = false;
        qApp.getIsUpbitService().postValue(false);
        QcToast.getInstance().show(TAG + " Destroy ! ", false);
    }
}
