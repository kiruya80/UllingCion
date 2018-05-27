package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcUtil;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.FragmentHomeBinding;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;
import com.ulling.ullingcion.model.CryptoWatchModel;
import com.ulling.ullingcion.model.MainModel;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.util.Utils;
import com.ulling.ullingcion.view.adapter.UpbitKrwAdapter;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;
import com.ulling.ullingcion.viewmodel.MainViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.math.BigDecimal;
import java.util.List;

public class HomeFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragmentHomeBinding viewBinding;

    private MainViewModel mMainModel;
    private CryptoWatchViewModel mCryptoWatchViewModel;
    private UpbitKrwViewModel mUpbitKrwViewModel;

    private UpbitUsdToKrwResponse mUpbitUsdToKrwResponse;
    private UpbitPriceResponse mUpbitPriceResponse;
    private CryptowatSummary mCryptowatSummary;

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
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
    }


    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragmentHomeBinding) getViewBinding();
    }

    @Override
    protected void needUIEventListener() {

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

//                    viewBinding.tvUsdToKrw.setText(
//                            upbitUsdToKrwResponses_.get(0).getDate() + " "
//                                    + upbitUsdToKrwResponses_.get(0).getTime() + "\n"
//                                    + "환율 1달러 : \n"
//                                    + upbitUsdToKrwResponses_.get(0).getBasePrice() + " 원");

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

}
