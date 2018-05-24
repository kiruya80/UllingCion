package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.databinding.FragmentCryptoSummaryBinding;
import com.ulling.ullingcion.entites.Cryptowat.CandlesLine;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.model.CryptoWatchModel;
import com.ulling.ullingcion.view.adapter.CryptoWatchAdapter;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CryptoSummaryFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragmentCryptoSummaryBinding viewBinding;
    private CryptoWatchViewModel viewModel;
    private CryptoWatchAdapter adapter;
    private SimpleDateFormat simpleDate;
    private int cryptoTimeType = Define.VALUE_CRYPTOWAT_4H;
    private boolean order = false;
    public CryptowatSummary cryptowatSummary;

    public static CryptoSummaryFragment newInstance() {
        CryptoSummaryFragment fragment = new CryptoSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.fragment_crypto_summary;
    }

    @Override
    protected void needOnShowToUser() {
        updateCryptoSummary();
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

        adapter = new CryptoWatchAdapter(this, null);

        simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+9"));

    }


    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragmentCryptoSummaryBinding) getViewBinding();
        viewBinding.qcRecyclerViewLow.setAdapter(adapter);
        viewBinding.qcRecyclerViewHigh.setAdapter(adapter);
    }

    @Override
    protected void needUIEventListener() {
        viewBinding.btnSupportLine.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (viewModel != null)
                    viewModel.setCandlesSupportLine(Integer.parseInt(viewBinding.editSupport.getText().toString()), 4000);
            }
        });
        viewBinding.editSupport.setText("" + 5);

    }

    @Override
    protected void needSubscribeUiFromViewModel() {
        viewModel = ViewModelProviders.of(this).get(CryptoWatchViewModel.class);
        viewModel.setViewModel(CryptoWatchModel.getInstance());


        viewModel.getSummary().observe(this, new Observer<CryptowatSummary>() {
            @Override
            public void onChanged(@Nullable CryptowatSummary cryptowatSummary_) {
                QcLog.e("observe getSummary == " + cryptowatSummary_.toString());
                cryptowatSummary = cryptowatSummary_;

                if (cryptowatSummary != null && cryptowatSummary.getResult() != null) {
//                    Double ladtPrice = Double.parseDouble(cryptowatSummary.getResult().getPrice().getLast());
                    viewBinding.tvLastPrice.setText("Last Price : " + cryptowatSummary.getResult().getPrice().getLast());
                    viewBinding.tvHighPrice.setText("High Price : " + cryptowatSummary.getResult().getPrice().getHigh());
                    viewBinding.tvLowPrice.setText("Low Price : " + cryptowatSummary.getResult().getPrice().getLow());

                    String getTime = simpleDate.format(System.currentTimeMillis());
                    viewBinding.tvTime.setText("" +getTime);
                    if (cryptowatSummary.getResult().getPrice().getChange().getPercentage() < 0) {
                        // 음봉
                        viewBinding.llCandle.setBackgroundColor(qCon.getResources().getColor(R.color.color_red));
                    } else {
                        // 양봉
                        viewBinding.llCandle.setBackgroundColor(qCon.getResources().getColor(R.color.color_green));
                    }
                    if (viewModel != null)
                        viewModel.setCandlesSupportLine(Integer.parseInt(viewBinding.editSupport.getText().toString()), 4000);
                }
            }
        });


        viewModel.getCandlesSupportLine().observe(this, new Observer<List<CandlesLine>>() {
            @Override
            public void onChanged(@Nullable List<CandlesLine> candlesAverages) {
                if (cryptowatSummary != null && candlesAverages != null) {
                    int index = 0;
                    for (int i = 0; i < candlesAverages.size(); i++) {
                        BigDecimal bd1 = new BigDecimal(String.valueOf(cryptowatSummary.getResult().getPrice().getLast()));
                        BigDecimal bd2 = new BigDecimal(String.valueOf(candlesAverages.get(i).getPrice()));

                        BigDecimal mBigDecimal = bd1.subtract(bd2);
                        if (mBigDecimal.intValue() < 0) {
                            // 마지막 시세가격보다 커지는 시점
                            index = i;
                            break;
                        }
                    }
                    int fromIndex = index - 4;
                    int toIndex = index + 4;
                    if (fromIndex < 0) {
                        fromIndex = 0;
                    }
                    if (toIndex > candlesAverages.size()) {
                        toIndex = candlesAverages.size();
                    }
                    List<CandlesLine> newCandles = candlesAverages.subList(fromIndex, toIndex);
                    viewBinding.tvPrice.setText("" + newCandles.toString());
                }
            }
        });
    }

    @Override
    protected void needSubscribeUiClear() {
    }


    private void updateCryptoSummary() {
        if (viewModel != null) {
            viewModel.loadSummary();
        }
    }



}
