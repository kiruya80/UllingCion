package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.FragmentUpbitKrwBinding;
import com.ulling.ullingcion.db.UpbitRoomDatabase;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.view.adapter.UpbitKrwAdapter;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UpbitKrwFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragmentUpbitKrwBinding viewBinding;
    private UpbitKrwViewModel mUpbitKrwViewModel;
    private UpbitKrwAdapter adapter;
    private String[] coinSymbol;

    public static UpbitKrwFragment newInstance() {
        UpbitKrwFragment fragment = new UpbitKrwFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.fragment_upbit_krw;
    }

    @Override
    protected void needOnShowToUser() {
        update();
    }

    @Override
    protected void needOnHiddenToUser() {
    }

    @Override
    protected void needInitToOnCreate() {
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        adapter = new UpbitKrwAdapter(this, null);
    }

    @Override
    protected void needResetData() {
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragmentUpbitKrwBinding) getViewBinding();
        viewBinding.qcRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void needUIEventListener() {
        viewBinding.btnGet.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                update();
            }
        });
    }

    @Override
    protected void needSubscribeUiFromViewModel() {
        mUpbitKrwViewModel = ViewModelProviders.of(this).get(UpbitKrwViewModel.class);
        mUpbitKrwViewModel.setViewModel(UpbitKrwModel.getInstance());

        mUpbitKrwViewModel.getKrwList().observe(this, new Observer<List<UpbitPriceResponse>>() {
            @Override
            public void onChanged(@Nullable List<UpbitPriceResponse> upbitPriceResponses) {
                QcLog.e("observe === " + upbitPriceResponses.toString());
                if (upbitPriceResponses != null && upbitPriceResponses.size() == 0) {
                    // 원화 상장 예정
                    QcLog.e("result 22== " + upbitPriceResponses);
                    if (adapter != null) {
                        adapter.addListDiffResult(mUpbitKrwViewModel.getKrwList().getValue());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        adapter.addList(mUpbitKrwViewModel.getKrwList().getValue());
    }

    private void update() {
        if (mUpbitKrwViewModel != null) {
            // 원화상자예정
//        https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-GTO&count=2&to=2018-04-20%2011:42:00
//            mUpbitKrwViewModel.loadKrwList("GTO", "1", "2018-04-07 11:42:00");

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
            String getTime = simpleDate.format(System.currentTimeMillis());

            coinSymbol = getResources().getStringArray(R.array.array_coin_symbol);
            if (coinSymbol != null)
                for (int i = 0; i < coinSymbol.length; i++) {
                    mUpbitKrwViewModel.loadKrwList(coinSymbol[i], "1", getTime);
                }

//        mUpbitKrwViewModel.loadKrwList("CRIX.UPBIT.KRW-EOS", "2", getTime);
        }
    }

    @Override
    protected void needSubscribeUiClear() {
    }

}
