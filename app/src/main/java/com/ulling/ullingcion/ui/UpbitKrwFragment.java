package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.databinding.FragmentUpbitKrwBinding;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.service.UpbitService;
import com.ulling.ullingcion.view.adapter.UpbitKrwAdapter;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UpbitKrwFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragmentUpbitKrwBinding viewBinding;
    private UpbitKrwViewModel mUpbitKrwViewModel;
    private UpbitKrwAdapter adapter;
    private ArrayList<String> coinSymbolList;

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
        List<UpbitPriceResponse> result = QcPreferences.getInstance().getList("upbit_krw_list_01", UpbitPriceResponse.class);
        if (result != null)
            QcLog.e("result == " + result.toString());
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
        viewBinding.qcRecyclerView.setAdapter(adapter, viewBinding.tvEmpty);
    }

    @Override
    protected void needUIEventListener() {
        viewBinding.btnServiceStart.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(qCon, UpbitService.class);
                qCon.stopService(intent);
                intent = new Intent(qCon, UpbitService.class);
                qCon.startService(intent);
            }
        });
        viewBinding.btnServiceStop.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(qCon, UpbitService.class);
                qCon.stopService(intent);
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
                QcLog.e("getKrwList observe === ");
                // 원화 상장 예정
                if (adapter != null) {
//                    List<UpbitPriceResponse> result = QcPreferences.getInstance().getList("upbit_krw_list_01", UpbitPriceResponse.class);
//                    QcLog.e("result 11== " + result.size() + " , " + result.toString());
//                    if (result.isEmpty())
//                        result = new ArrayList<UpbitPriceResponse>();
//                    result.add(upbitPriceResponses.get(0));
//                    QcLog.e("result 22== " + result.size() + " , " + result.toString());
//                    QcPreferences.getInstance().putList("upbit_krw_list_01", result);

                    adapter.addListDiffResult(upbitPriceResponses);
                }
            }
        });

        qApp.getIsUpbitService().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                QcLog.e("getIsUpbitService == " + aBoolean);
                if (aBoolean) {
                    viewBinding.tvServiceStatus.setText("Service Status : START !");
                } else {
                    viewBinding.tvServiceStatus.setText("Service Status : STOP");
                }
            }
        });
//        adapter.addList(mUpbitKrwViewModel.getKrwList().getValue());
    }

    private void update() {
        if (mUpbitKrwViewModel != null) {
            // 원화상자예정
//        https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-GTO&count=2&to=2018-04-20%2011:42:00
//            mUpbitKrwViewModel.loadKrwList("GTO", "1", "2018-04-07 11:42:00");

            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
            String getTime = simpleDate.format(System.currentTimeMillis());

//            coinSymbol = getResources().getStringArray(R.array.array_coin_symbol);
//            if (coinSymbol != null)
//                for (int i = 0; i < coinSymbol.length; i++)
//                    mUpbitKrwViewModel.loadKrwList(coinSymbol[i], "1", getTime);


            String[] coinSymbol = getResources().getStringArray(R.array.array_coin_symbol);

            coinSymbolList = new ArrayList<>(Arrays.asList(coinSymbol));
            if (coinSymbolList == null) {
                return;
            }

            List<UpbitPriceResponse> result = QcPreferences.getInstance().getList(Define.PRE_UPBIT_KRW_LIST, UpbitPriceResponse.class);
            QcLog.e("getKrwList observe 11 == " + result.size() + " , " + result.toString());
            if (result.isEmpty())
                result = new ArrayList<UpbitPriceResponse>();

            for (int i = 0; i < coinSymbolList.size(); i++) {
                for (int j = 0; j < result.size(); j++) {
                    if (!coinSymbolList.get(i).equals(result.get(j).getCode())) {
                        coinSymbolList.remove(i);
                    }
                }
            }

            if (coinSymbolList.size() == 0) {
                return;
            }
            for (int i = 0; i < coinSymbolList.size(); i++) {
                mUpbitKrwViewModel.loadKrwList(coinSymbolList.get(i), "1", getTime);
            }

        }
    }

    @Override
    protected void needSubscribeUiClear() {
    }

}
