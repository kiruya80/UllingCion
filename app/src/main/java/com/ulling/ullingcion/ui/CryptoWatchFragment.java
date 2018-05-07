package com.ulling.ullingcion.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.FragmentCryptowatchBinding;
import com.ulling.ullingcion.view.adapter.CryptoWatchAdapter;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;

/**
 *
 * View는 앱에서 유저 인터페이스의 실질적인 부분입니다. Activity , Fragment 안드로이드 View 도 이 View가 될 수 있습니다.
 * 이 View의 onResume() onPause() 에서 이벤트 소스를 바인딩, 언바인딩 하게됩니다.
 */
public class CryptoWatchFragment extends QcBaseShowLifeFragement implements SwipeRefreshLayout.OnRefreshListener {

    private QUllingApplication qApp;
    private FragmentCryptowatchBinding viewBinding;
    private CryptoWatchViewModel viewModel;
    private CryptoWatchAdapter adapter;

    public static CryptoWatchFragment newInstance() {
        CryptoWatchFragment fragment = new CryptoWatchFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int needGetLayoutId() {
        return R.layout.fragment_cryptowatch;
    }


    @Override
    protected void needResetData() {
    }

    @Override
    protected void needOnShowToUser() {
    }

    @Override
    protected void needOnHiddenToUser() {
    }


    @Override
    protected void needInitToOnCreate() {
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(CryptoWatchViewModel.class);
//            viewModel.needInitViewModel(getActivity(), this);
//            viewModel.needDatabaseModel(DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_CRYPTOWATCH_URL);
        }
//        adapter = new CryptoWatchAdapter(this, qcRecyclerItemListener);
//        if (adapter != null)
//            adapter.setViewModel(viewModel);
    }


    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragmentCryptowatchBinding) getViewBinding();
    }

    @Override
    protected void needUIEventListener() {

    }

    @Override
    protected void needSubscribeUiFromViewModel() {

    }

    @Override
    protected void needSubscribeUiClear() {

    }


    @Override
    public void onRefresh() {

    }

    private QcRecyclerBaseAdapter.QcRecyclerItemListener qcRecyclerItemListener = new QcRecyclerBaseAdapter.QcRecyclerItemListener() {

        @Override
        public void onItemClick(View view, int position, Object o) {

        }

        @Override
        public void onItemLongClick(View view, int position, Object o) {

        }

        @Override
        public void onItemCheck(boolean checked, int position, Object o) {

        }

        @Override
        public void onDeleteItem(int itemPosition, Object o) {

        }

        @Override
        public void onReload() {

        }
    };
}