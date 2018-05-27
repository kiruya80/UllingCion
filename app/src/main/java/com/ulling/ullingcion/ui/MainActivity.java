package com.ulling.ullingcion.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.ui.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.QUllingApplication;
import com.ulling.ullingcion.R;
import com.ulling.ullingcion.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends QcBaseLifeActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private QUllingApplication qApp;
    private ArrayList<QcBaseShowLifeFragement> BaseQLifecycleFragmentList = new ArrayList<>();

    ActivityMainBinding viewBinding;

    @Override
    protected int needGetLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void needInitToOnCreate() {

    }

    @Override
    protected void optGetArgument(Bundle savedInstanceState) {

    }

    @Override
    protected void needResetData() {
        qApp = QUllingApplication.getInstance();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        setFragment();
    }

    @Override
    protected void needUIBinding() {
        viewBinding = (ActivityMainBinding) getViewDataBinding();
        viewBinding.container.setAdapter(mSectionsPagerAdapter);
        viewBinding.tabs.setupWithViewPager(viewBinding.container);
        viewBinding.tabs.setTabMode(TabLayout.MODE_FIXED);
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


    private void setFragment() {
        BaseQLifecycleFragmentList.add(HomeFragment.newInstance());
        BaseQLifecycleFragmentList.add(CryptoWatchFragment.newInstance());
        BaseQLifecycleFragmentList.add(CryptoSummaryFragment.newInstance());
        BaseQLifecycleFragmentList.add(UpbitKrwFragment.newInstance());
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"홈", "크립토워치 캔들", "크립토 예측", "업비트 원화마켓"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BaseQLifecycleFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return BaseQLifecycleFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
