package com.ulling.ullingcion.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcUtil;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.databinding.FragmentCryptowatchBinding;
import com.ulling.ullingcion.entites.Cryptowat.Candles;
import com.ulling.ullingcion.entites.Cryptowat.CandlesLine;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.model.CryptoWatchModel;
import com.ulling.ullingcion.view.adapter.CryptoDiffCallback;
import com.ulling.ullingcion.view.adapter.CryptoWatchAdapter;
import com.ulling.ullingcion.viewmodel.CryptoWatchViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * View는 앱에서 유저 인터페이스의 실질적인 부분입니다. Activity , Fragment 안드로이드 View 도 이 View가 될 수 있습니다.
 * 이 View의 onResume() onPause() 에서 이벤트 소스를 바인딩, 언바인딩 하게됩니다.
 */
public class CryptoWatchFragment extends QcBaseShowLifeFragement {

    private QUllingApplication qApp;
    private FragmentCryptowatchBinding viewBinding;
    private CryptoWatchViewModel viewModel;
    private CryptoWatchAdapter adapter;
    private SimpleDateFormat simpleDate;
    private int cryptoTimeType = Define.VALUE_CRYPTOWAT_4H;
    private boolean order = false;
    public CryptowatSummary cryptowatSummary;


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
//         diffCallback = new CryptoDiffCallback(this.itemList, mNewItemList);
        adapter = new CryptoWatchAdapter(this, null);

        simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+9"));

    }


    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragmentCryptowatchBinding) getViewBinding();
        viewBinding.qcRecyclerView.setAdapter(adapter, viewBinding.tvEmpty);
    }

    @Override
    protected void needUIEventListener() {
        viewBinding.btnGet.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                updateCryptoCandles();
            }
        });
        viewBinding.btnOrder.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (adapter != null) {
                    QcLog.e("btnOrder === " + order);
                    order = !order;
                    adapter.addAll(sortByCloseTime(viewModel.getCandles().getValue(), order));
                }
            }
        });
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
                }
            }
        });

        viewModel.getCandles().observe(this, new Observer<List<Candles>>() {
            @Override
            public void onChanged(@Nullable List<Candles> candles) {
                QcLog.e("observe getCandles == " + candles.size());
                if (adapter != null) {
//                    adapter.addListDiffResult(sortByCloseTime(candles, order), new CryptoDiffCallback(adapter.itemList, candles));
                    List<Candles> candles_ = new ArrayList<>();
                    candles_.addAll(candles.subList(0, 50));
                    adapter.addListDiffResult(candles_, new CryptoDiffCallback(adapter.itemList, candles));

//                    adapter.addListDiffResult(candles, new CryptoDiffCallback(adapter.itemList, candles));
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
                    viewBinding.tvSupportLine.setText("" + newCandles.toString());
                }
            }
        });
    }

//    public static List<CandlesLine> sortByPrice(List<CandlesLine> oldList, final boolean asc) {
//        Collections.sort(oldList, new Comparator<CandlesLine>() {
//            @Override
//            public int compare(CandlesLine candlesAverage1, CandlesLine candlesAverage2) {
//                if (asc) {
//                    return (int) (candlesAverage1.getCount() - candlesAverage2.getCount());
//                } else {
//                    return (int) (candlesAverage2.getCount() - candlesAverage1.getCount());
//                }
//            }
//        });
//        return oldList;
//    }

    public static List<Candles> sortByCloseTime(List<Candles> oldList, final boolean asc) {
        Collections.sort(oldList, new Comparator<Candles>() {
            @Override
            public int compare(Candles candles1, Candles candles2) {
                BigDecimal bd1 = new BigDecimal(String.valueOf(candles1.getCloseTime() / 10000));
                BigDecimal bd2 = new BigDecimal(String.valueOf(candles2.getCloseTime() / 10000));
                if (asc) {
//                    return (int) (candles1.getCloseTime() - candles2.getCloseTime());
                    BigDecimal mBigDecimal = bd1.subtract(bd2);
                    return mBigDecimal.intValue();
                } else {
//                    return (int) (candles2.getCloseTime() - candles1.getCloseTime());
                    BigDecimal mBigDecimal = bd2.subtract(bd1);
                    return mBigDecimal.intValue();
                }
            }
        });
        return oldList;
    }

    @Override
    protected void needSubscribeUiClear() {

    }

    private void updateCryptoSummary() {
        if (viewModel != null) {
            viewModel.loadSummary();
        }
    }

    private void updateCryptoCandles() {
        if (viewModel != null) {
//            cryptoTimeType = Define.VALUE_CRYPTOWAT_1W;
            Date after = QcUtil.GetDate(2017, 10, 1, 0, 0, 0);
            viewModel.loadCandlesStick(QcUtil.GetUnixTime(after.getTime()), cryptoTimeType);

        }
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