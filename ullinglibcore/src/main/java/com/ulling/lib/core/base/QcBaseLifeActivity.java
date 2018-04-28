package com.ulling.lib.core.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-07-04.
 */
public abstract class QcBaseLifeActivity extends AppCompatActivity {
    public Context qCon;
    public String APP_NAME;

    /**
     * 필수
     * need~ 시작
     */


    /**
     * 1-1.
     *
     * 프레그먼트 UI 데이터 초기화
     */
    protected abstract void needInitToOnCreate();
    /**
     * 1-2.
     *
     * 프레그먼트 UI 데이터 리셋
     */
    protected abstract void needResetData();

    /**
     * 2.
     *
     * 뷰모델 초기화
     */
    protected abstract void needInitViewModel();
    /**
     * 3.
     *
     * 설정한 레이아웃 아이디를 가지고
     * onCreateView 에서 자동으로 바인딩된다
     * rootViewBinding = DataBindingUtil.inflate(inflater, needGetLayoutId(), container, false);
     *
     * @return 레이아웃 아이디 클래스이름을 기준으로 생성
     *
     * ex) LiveDataFragment -> R.layout.frag_user_profile;
     */
    protected abstract int needGetLayoutId();


    /**
     * 3.
     *
     * UI에서 필요한 데이터 바인딩
     * View객체에 접근하여 데이터 연결한다.
     */
    protected abstract void needUIBinding();

    /**
     * 4.
     * 접근한 View에 이벤트에 따른 동작 설정
     * 버튼 및 기타 UI이벤트 설정
     */
    protected abstract void needUIEventListener();


    /**
     * 6.
     *
     * 데이터모델로부터 변화되는 데이터를 구독하고
     * 데이터를 UI에 연결한다
     */
    protected abstract void needSubscribeUiFromViewModel();
    /**
     * 옵션
     * opt
     *
     * animationResume
     * animationPause
     */
    /**
     * 옵션
     * 프레그먼트 Destroy되는 경우 데이터 리셋
     */
    protected void needDestroyData() {
    }

    /**
     * 데이터 전달시 가져오기
     * LiveData로 활용하능한지는 체크해봐야함 !!
     * 또한 데이터가 필요한지도 확인 필요
     */
    protected void optGetArgument(Bundle savedInstanceState) {
    }

    /**
     * 애니메이션 시작
     */
    protected void optAnimationResume() {
    }


    /**
     * 애니메이션 정지
     */
    protected void optAnimationPause() {
    }

    protected void onActivityResultOk(int requestCode, Intent data) {
        QcLog.i("RESULT_OK requestCode == " + requestCode);
//        if (requestCode == REQUEST_ACT) {
//            String resultMsg = data.getStringExtra("result_msg");
//        }
    }

    protected void onActivityResultCancle() {
        QcLog.i("RESULT_CANCELED == ");
    }

    /**
     * Lifecycle
     *
     * onCreate() > ( onRestart() )  > onStart() > onResume() > onPause() > onStop() > onDestroy()
     */
    /**
     * Activity에서의 onCreate()와 비슷하나, ui관련 작업은 할 수 없다.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.i("onCreate");
        qCon = getApplication().getApplicationContext();
//        setContentView(needGetLayoutId());

        needInitToOnCreate();
        needResetData();
        needUIBinding();
        optGetArgument(savedInstanceState);
        needSubscribeUiFromViewModel();
    }

    /**
     * layout id binding
     */
    public ViewDataBinding getViewDataBinding() {
        return DataBindingUtil.setContentView(this, needGetLayoutId());
    }

    /**
     */
    @Override
    public void onRestart() {
        super.onRestart();
        QcLog.i("onRestart == ");
    }

    /**
     * 액티비티가 사용자에게 표시되기 직전에 호출
     * 액티비티가 전경으로 나오면 onResume()
     * 액티비티가 숨겨지면 onStop()
     */
    @Override
    public void onStart() {
        super.onStart();
        QcLog.i("onStart == ");
    }

    /**
     * 액티비티가 시작되고 사용자와 상호 작용하기 직전에 호출
     */
    @Override
    public void onResume() {
        super.onResume();
        QcLog.i("onResume == ");
        optAnimationResume();
    }

    /**
     * 시스템이 다른 액티비티를 재개하기 직전에 호출
     * CPU를 소모하는 기타 작업들을 중단하는 등 여러 가지 용도에 사용
     * 이 메서드는 무슨 일을 하든 매우 빨리 끝내야함
     */
    @Override
    public void onPause() {
        super.onPause();
        QcLog.i("onPause == ");
        optAnimationPause();
    }

    /**
     * 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출
     * 액티비티가 다시 사용자와 상호 작용하면 onRestart()
     */
    @Override
    public void onStop() {
        super.onStop();
        QcLog.i("onStop == ");
    }

    /**
     * 액티비티가 사라지면 onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.i("onDestroy == ");
        needDestroyData();
    }

    /**
     * //    int REQUEST_ACT = 10;
     * 호출
     * //    Intent intent = new Intent(Activity_A.this, Activity_B.class);
     * //    startActivityForResult(intent, REQUEST_ACT);
     *
     * 돌아오기
     * //    Intent intent = new Intent();
     * //    intent.putExtra("result_msg","결과가 넘어간다 얍!");
     * //    setResult(RESULT_OK, intent);
     * //    finish();
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            onActivityResultOk(requestCode, data);
        } else {
            onActivityResultCancle();

        }
    }

}
