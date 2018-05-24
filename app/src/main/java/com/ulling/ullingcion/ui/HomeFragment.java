package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.FragmentHomeBinding;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;
import com.ulling.ullingcion.model.MainModel;
import com.ulling.ullingcion.model.UpbitKrwModel;
import com.ulling.ullingcion.view.adapter.UpbitKrwAdapter;
import com.ulling.ullingcion.viewmodel.MainViewModel;
import com.ulling.ullingcion.viewmodel.UpbitKrwViewModel;

import java.util.List;

public class HomeFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragmentHomeBinding viewBinding;

    private MainViewModel mMainModel;

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

        mMainModel.getUsdToKrw().observe(this, new Observer<List<UpbitUsdToKrwResponse>>() {
            @Override
            public void onChanged(@Nullable List<UpbitUsdToKrwResponse> upbitUsdToKrwResponses) {
                if (upbitUsdToKrwResponses != null && upbitUsdToKrwResponses.size() > 0) {
                    viewBinding.tvUsdToKrw.setText(
                            upbitUsdToKrwResponses.get(0).getDate() + " " + upbitUsdToKrwResponses.get(0).getTime() + "\n"
                                    + "환율 1달러 : \n" + upbitUsdToKrwResponses.get(0).getBasePrice() + " 원");
                }
            }
        });

    }

    @Override
    protected void needSubscribeUiClear() {
    }

    private void updateUsdToKrw() {
        if (mMainModel != null)
            mMainModel.loadUsdToKrw();
    }

}
