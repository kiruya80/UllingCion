package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcUtil;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.databinding.FragmentHomeBinding;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;
import com.ulling.ullingcion.model.CryptoWatchModel;
import com.ulling.ullingcion.model.MainModel;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;
import com.ulling.ullingcion.viewmodel.MainViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragmentHomeBinding viewBinding;

    private MainViewModel mMainModel;
    private CryptoWatchViewModel mCryptoWatchViewModel;
    private UpbitKrwViewModel mUpbitKrwViewModel;

    private UpbitUsdToKrwResponse mUpbitUsdToKrwResponse;
    private UpbitPriceResponse mUpbitPriceResponse;
    private CryptowatSummary mCryptowatSummary;
    private SimpleDateFormat simpleDate;
    private List<Integer> btcPriceList;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void needOnShowToUser() {
        updateUsdToKrw();
    }

    @Override
    protected void needOnHiddenToUser() {
    }

    @Override
    protected void needResetData() {
    }

    @Override
    protected void needInitToOnCreate() {
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        simpleDate = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.KOREA);
        mUpbitUsdToKrwResponse = QcPreferences.getInstance().get(Define.PRE_USD_TO_KRW, UpbitUsdToKrwResponse.class);
    }

    @Override
    protected void needUIBinding() {
        viewBinding = (FragmentHomeBinding) getViewBinding();
        btcPriceList = QcPreferences.getInstance().getList(Define.PRE_BTC_PRICE, Integer.class);
        viewBinding.tvBtcPrice.setText(btcPriceList.toString());
    }

    @Override
    protected void needUIEventListener() {
        viewBinding.btnPrice.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (viewBinding.editBtcPrice.getText() != null
                        && viewBinding.editBtcPrice.getText().toString() != null
                        && !viewBinding.editBtcPrice.getText().toString().isEmpty()) {
                    setBtcPrice(Integer.parseInt(viewBinding.editBtcPrice.getText().toString()));
                    viewBinding.editBtcPrice.setText("");
                    viewBinding.editBtcPrice.clearFocus();
                    QcUtil.hiddenSoftKey(qCon, viewBinding.editBtcPrice);
                }
            }
        });

        viewBinding.btnResetPrice.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                btcPriceList = new ArrayList<>();
                QcPreferences.getInstance().putList(Define.PRE_BTC_PRICE, btcPriceList);
                viewBinding.tvBtcPrice.setText(btcPriceList.toString());
            }
        });
    }

    @Override
    protected void needSubscribeUiFromViewModel() {
        mMainModel = new MainViewModel(qApp);
        mMainModel.setViewModel(MainModel.getInstance());

        mUpbitKrwViewModel = new UpbitKrwViewModel(qApp);
        mUpbitKrwViewModel.setViewModel(UpbitKrwModel.getInstance());

        mCryptoWatchViewModel = new CryptoWatchViewModel(qApp);
        mCryptoWatchViewModel.setViewModel(CryptoWatchModel.getInstance());

        mMainModel.getUsdToKrw().observe(this, new Observer<List<UpbitUsdToKrwResponse>>() {
            @Override
            public void onChanged(@Nullable List<UpbitUsdToKrwResponse> upbitUsdToKrwResponses) {
                if (upbitUsdToKrwResponses != null && upbitUsdToKrwResponses.size() > 0) {
                    mUpbitUsdToKrwResponse = upbitUsdToKrwResponses.get(0);
                    QcLog.e("getUsdToKrw === " + mUpbitUsdToKrwResponse.toString());
                    getPremiumBtcPrice();
                }
            }
        });

        mUpbitKrwViewModel.getCoinPrice().observe(this, new Observer<UpbitPriceResponse>() {
            @Override
            public void onChanged(@Nullable UpbitPriceResponse upbitPriceResponse) {
                if (upbitPriceResponse != null) {
                    QcLog.e("getCoinPrice === " + upbitPriceResponse.toString());
                    mUpbitPriceResponse = upbitPriceResponse;
                    getPremiumBtcPrice();
                }
            }
        });

        if (mCryptoWatchViewModel != null) {
            mCryptoWatchViewModel.getSummary().observe(this, new Observer<CryptowatSummary>() {
                @Override
                public void onChanged(@Nullable CryptowatSummary cryptowatSummary) {
                    if (cryptowatSummary != null) {
                        QcLog.e("getSummary === " + cryptowatSummary.toString());
                        mCryptowatSummary = cryptowatSummary;
                        getPremiumBtcPrice();
                    }

                }
            });
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

            viewBinding.tvUsdToKrw.setText(
                    mUpbitUsdToKrwResponse.getDate() + "\n"
                            + simpleDate.format(mUpbitPriceResponse.getTimestamp()) + "\n"
                            + "환율 1달러 : " + QcUtil.toNumFormat(mUpbitUsdToKrwResponse.getBasePrice()) + " 원" + "\n"
                            + "Bitfinex 가격 : " + QcUtil.toNumFormat(cryptoBtcUsd) + " 원\n"
                            + QcUtil.toNumFormat(mCryptowatSummary.getResult().getPrice().getLast()) + "달러\n"
                            + "업비트 가격 : " + QcUtil.toNumFormat(upbitBtcPrice) + " 원 \n"
                            + "프리미엄 : " + QcUtil.toNumFormat(premium.intValue()) + " ("
                            + QcUtil.toNumFormat(premiumPercent.doubleValue()) + "%)");
        }
    }

    @Override
    protected void needSubscribeUiClear() {
    }

    private void updateUsdToKrw() {
        if (mMainModel != null)
            mMainModel.loadUsdToKrw();
    }

    private void setBtcPrice(int btcPrice) {
        btcPriceList = QcPreferences.getInstance().getList(Define.PRE_BTC_PRICE, Integer.class);
        if (btcPriceList == null) {
            btcPriceList = new ArrayList<>();
        }

        for (int i = 0; i < btcPriceList.size(); i++) {
            if (btcPriceList.get(i) == btcPrice) {
                return;
            }
        }

        btcPriceList.add(btcPrice);
        btcPriceList = sortByPrice(btcPriceList, true);
        QcPreferences.getInstance().putList(Define.PRE_BTC_PRICE, btcPriceList);
        btcPriceList = QcPreferences.getInstance().getList(Define.PRE_BTC_PRICE, Integer.class);
        viewBinding.tvBtcPrice.setText(btcPriceList.toString());
    }

    public static List<Integer> sortByPrice(List<Integer> oldList, final boolean asc) {
        Collections.sort(oldList, new Comparator<Integer>() {
            @Override
            public int compare(Integer int01, Integer int02) {
                if (asc) {
                    return int01 - int02;
                } else {
                    return int02 - int01;
                }
            }
        });
        return oldList;
    }
}
