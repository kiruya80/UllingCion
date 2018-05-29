/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.ullingcion.common;

import android.Manifest;

import com.ulling.lib.core.common.QcDefine;

public class Define extends QcDefine {
    /**
     * 상용/ 테스트용
     */
//    public static final boolean DEBUG_FLAG = true;

    public static final int PAGE_SIZE = 20;

    /**
     * TimeOut
     */
    public static final int INTRO_TIMEOUT = 1500;

    public static final String PRE_USD_TO_KRW = "PRE_USD_TO_KRW";
    public static final String PRE_UPBIT_KRW_LIST = "PRE_UPBIT_KRW_LIST";
    public static final String PRE_BTC_PRICE = "PRE_BTC_PRICE";

    public static final String LINE_AUTH = "Bearer ceN0PqIIuEdA9acv35az7YCyVQcdXJtOf1GwgAr4oxN";


    public static final int VALUE_CRYPTOWAT_1M = 60;
    public static final int VALUE_CRYPTOWAT_3M = 180;
    public static final int VALUE_CRYPTOWAT_5M = 300;
    public static final int VALUE_CRYPTOWAT_15M = 900;
    public static final int VALUE_CRYPTOWAT_30M = 1800;

    public static final int VALUE_CRYPTOWAT_1H = 3600;
    public static final int VALUE_CRYPTOWAT_2H = 7200;
    public static final int VALUE_CRYPTOWAT_4H = 14400;
    public static final int VALUE_CRYPTOWAT_6H = 21600;
    public static final int VALUE_CRYPTOWAT_12H = 43200;

    public static final int VALUE_CRYPTOWAT_1D = 86400;
    public static final int VALUE_CRYPTOWAT_3D = 259200;
    public static final int VALUE_CRYPTOWAT_1W = 604800;

}
