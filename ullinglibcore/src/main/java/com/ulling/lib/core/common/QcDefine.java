/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.core.common;

import android.Manifest;

public class QcDefine {
    /**
     * 상용/ 테스트용
     */
    public static final boolean DEBUG_FLAG = true;

    //    public final static boolean IS_REAL = true;// true 상용, false 개발
//    protected static final String URL = (IS_REAL ? REAL_CONN : DEV_CONN);
//    public static final String SP_APP_NAME = "";
    public static final String SP_COMPANNY = "ULLING";

    public static final String OS = "ANDROID";
    public static final String APP_VERSION = "APP_VERSION";
    public static final String USER_U_ID = "USER_U_ID";
    public static final String TOKEN = "TOKEN";
    public static final String USER_INFO = "USER_INFO";
    public static final String USER_GCM_ID = "USER_GCM_ID";
    public static final String DEVICE_U_ID = "p_";

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
