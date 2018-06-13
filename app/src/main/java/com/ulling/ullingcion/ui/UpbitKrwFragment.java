package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.network.QcBaseRetrofitService;
import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.databinding.FragmentUpbitKrwBinding;
import com.ulling.ullingcion.entites.UpbitPriceResponse;
import com.ulling.ullingcion.model.LineModel;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.service.UpbitService;
import com.ulling.ullingcion.view.adapter.UpbitKrwAdapter;
import com.ulling.ullingcion.viewmodel.LineViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpbitKrwFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragmentUpbitKrwBinding viewBinding;
    private UpbitKrwViewModel mUpbitKrwViewModel;
    private UpbitKrwAdapter adapter;

    LineViewModel mLineViewModel;

    public static UpbitKrwFragment newInstance() {
        UpbitKrwFragment fragment = new UpbitKrwFragment();
        Bundle args = new Bundle();
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
    protected void needResetData() {
    }

    @Override
    protected void needInitToOnCreate() {
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        adapter = new UpbitKrwAdapter(this, null);
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
//                Intent intent = new Intent(qCon, UpbitService.class);
//                qCon.stopService(intent);
//                intent = new Intent(qCon, UpbitService.class);
//                qCon.startService(intent);
            }
        });
        viewBinding.btnServiceStop.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//                Intent intent = new Intent(qCon, UpbitService.class);
//                qCon.stopService(intent);
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
    }

    public void initLineViewModel() {
        mLineViewModel = new LineViewModel(qApp);
        mLineViewModel.setViewModel(LineModel.getInstance());
    }

    @Override
    protected void needSubscribeUiClear() {
    }

    private QcBaseRetrofitService.OnRetrofitListener mOnRetrofitListener
            = new QcBaseRetrofitService.OnRetrofitListener<UpbitPriceResponse>() {

        @Override
        public void onSuccessful() {

        }

        @Override
        public void onErrorBody(UpbitPriceResponse upbitPriceResponse) {

        }

        @Override
        public void onFailure(String msg) {

        }
    };
}
