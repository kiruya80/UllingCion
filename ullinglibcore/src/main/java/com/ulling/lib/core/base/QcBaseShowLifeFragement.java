package com.ulling.lib.core.base;

import android.os.Bundle;
import android.view.View;

import com.ulling.lib.core.util.QcLog;

/**
 * 현재 보이는 프레그먼트
 * 보이는 경우
 * needPageVisiableToUser가 호출된다
 */
public abstract class QcBaseShowLifeFragement extends QcBaseLifeFragment {
    /**
     * 뷰 초기화 체크
     */
    public boolean isViewPrepared;
    // 사용자에게 현재뷰가 보여지는 경우 플래그
//    private boolean isShowToUser;


    /**
     * 페이져뷰등에서 화면에 현재 프레그먼트가 보일때 실행됨
     */
    protected abstract void needOnShowToUser();

    protected abstract void needOnHiddenToUser();

    protected abstract void onDestroyToUser();
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        isShowToUser = false;
//        isViewPrepared = false;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        isShowToUser = false;
//        isViewPrepared = false;
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 뷰 초기화가 안된 경우 패스
        if (!isViewPrepared)
            return;

        /**
         * QcBaseLifeFragment 의 resume에서 애니메이션 플레그 막기
         */
        isResumeAnimation = false;
        QcLog.i("setUserVisibleHint == " + sectionPosition + " , " + isVisibleToUser + " ," + isViewPrepared);
//        if (isVisibleToUser && isViewPrepared) {
//            isShowToUser = true;
//            needOnShowToUser();
//        } else {
//            isShowToUser = false;
//            optAnimationPause();
//        }
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        } else {
            needOnHiddenToUser();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        needResetData();
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
//        if (!isShowToUser && getUserVisibleHint()) {
//            isShowToUser = true;
//            needOnShowToUser();
//        }
    }

    @Override
    protected void optGetArgument(Bundle savedInstanceState) {
        super.optGetArgument(savedInstanceState);
        if (getArguments() != null) {
            sectionPosition = getArguments().getInt(ARG_SECTION_POSITION);
        } else {
            sectionPosition = -1;
        }
    }


    private void lazyFetchDataIfPrepared() {
        QcLog.i("lazyFetchDataIfPrepared   === " +
                getUserVisibleHint() + " , " + sectionPosition + " , " + isViewPrepared);
        if (getUserVisibleHint() && isViewPrepared) {
            // 최초 한번만 로딩하고 이후에는 그대로인 경우 플래그
            needOnShowToUser();
        }
    }

    public int getSectionPosition() {
        return sectionPosition;
    }
}
