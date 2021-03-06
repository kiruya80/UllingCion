package com.ulling.lib.core.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.util.Base64;

import com.ulling.lib.core.common.QcDefine;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 2017.07.19
 * <p>
 * 4.4	KitKat	19	17.1%
 * 5.0	Lollipop	21	7.8%
 * 5.1	22	22.3%
 * 6.0	Marshmallow	23	31.8%
 * 7.0	Nougat	24	10.6%
 * 7.1	25	0.9%
 * <p>
 * FlexboxLayout 뜯어보기
 * http://www.kmshack.kr/2017/03/flexboxlayout-%EB%9C%AF%EC%96%B4%EB%B3%B4%EA%B8%B0/
 * <p>
 * CoordinatorLayout과 Behavior의 관계
 * >> 스크롤하는 경우 상단등 자식뷰들이 변화하게
 * http://www.kmshack.kr/tag/coordinatorlayout/
 * <p>
 * adapter modelview holder 최적화
 * https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
 * https://medium.com/@taman.neupane/basic-example-of-livedata-and-viewmodel-14d5af922d0
 * <p>
 * NestedScrollView
 * <p>
 * BottomSheetBehavior
 * <p>
 * <p>
 * Transition Animation
 * 화면 전환 애니메이션
 * android:transitionName
 * http://jhson5183.tistory.com/2
 * https://developer.android.com/training/material/animations.html?hl=ko
 */
public class QcBaseApplication extends Application {
    //    public String TAG = getClass().getSimpleName();
    private static QcBaseApplication SINGLE_U;
    public static String APP_NAME = "";
    public AppExecutors mAppExecutors;

    public static synchronized QcBaseApplication getInstance() {
        return SINGLE_U;
    }

    /**
     * 애플리케이션이 생성될 때 호출된다. 모든 상태변수와 리소스 초기화한다
     */
    @Override
    public void onCreate() {
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
        }
        super.onCreate();
        init();
    }

    /**
     * @MethodName : init
     * @Date : 2015. 3. 15.
     * @author : KILHO
     * @Method ㄴ 초기화
     * @변경이력
     */
    private void init() {
        QcLog.i("QbaseApplication init !");
        SINGLE_U = this;
        QcPreferences.getInstance();
        QcToast.getInstance();
        mAppExecutors = new AppExecutors();
        if (QcDefine.DEBUG_FLAG)
            getHashKey();
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                QcLog.e("key hash 해시키 ===== " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 애플리케이션 객체가 종료될 때 호출되는데 항상 보증하지 않는다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        QcLog.i("QbaseApplication onTerminate !");
    }

    /**
     * 애플리케이션은 구성변경을 위해 재시작하지 않는다.
     * 변경이 필요하다면 이곳에서 핸들러를 재정의 하면 된다.
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        QcLog.i("QbaseApplication onConfigurationChanged !");
    }

    /**
     * 시스템이 리소스가 부족할 때 발생한다.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        QcLog.e("QbaseApplication onLowMemory !");
    }

    public static String getAppName() {
        return APP_NAME;
    }
}