/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.ullingcion.common;

import android.Manifest;

public class QcDefine {
    /**
     * 상용/ 테스트용
     */
    public static final boolean DEBUG_FLAG = true;

    public static final String SP_COMPANNY = "ULLING";

    public static final String OS = "ANDROID";
    public static final String APP_VERSION = "app_version";

    public static final int PAGE_SIZE = 20;

    /**
     * TimeOut
     */
    public static final int INTRO_TIMEOUT = 1500;

    /**
     * UTC 서버 데이트 타입 yyyy-MM-dd HH:mm:ss
     */
    public static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * 앱내에서 보여지는 데이트 타입 MMM. d, yyyy
     */
    public static final String LOCAL_LIST_DATE_FORMAT = "MMM. dd, HH:mm";

    public static final String ACCESS_NETWORK_MOBILE = "ACCESS_NETWORK_MOBILE";

    public static final String[] PERMISSIONS_CAMERA_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

}
