package com.ulling.ullingcion.common;

import com.ulling.lib.core.common.QcApiURL;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 17.
 * @description :
 * @since :
 */

public class ApiUrl extends QcApiURL {

    public static final String BASE_URL = "https://api.stackexchange.com/2.2/";


    //    https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-XVG&count=2&to=2018-04-27%2009:29:00
//    https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-SNT&count=2&to=2018-01-02%2000:00:00
//        public static final String BASE_UPBIT_URL = "https://crix-api-cdn.upbit.com/v1/";
        public static final String BASE_UPBIT_URL = "https://crix-api-endpoint.upbit.com/";



//    https://api.cryptowat.ch/markets/bitfinex/btcusd/ohlc?periods=86400&after=1522422000
        public static final String BASE_CRYPTOWATCH_URL = "https://api.cryptowat.ch/";

//    https://static.upbit.com/logos/ETH.png
    public static final String BASE_COIN_LOGO_URL = "https://static.upbit.com/logos/";


    // https://notify-api.line.me/api/notify
    public static final String BASE_LINE_URL = "https://notify-api.line.me/";


}
