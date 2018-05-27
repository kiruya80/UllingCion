package com.ulling.ullingcion.network;

import com.ulling.ullingcion.entites.UpbitPriceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by P100651 on 2017-07-04.
 *
 * https://futurestud.io/tutorials/retrofit-2-add-multiple-query-parameter-with-querymap
 *
 * https://stackoverflow.com/questions/36730086/retrofit-2-url-query-parameter
 *
 */
public interface UpbitApi {

//    https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-XVG&count=2&to=2018-04-27%2009:29:00
//    1?code=CRIX.UPBIT.KRW-XVG&count=2&to=2018-04-27%2009:29:00
//    @GET("/1?code=CRIX.UPBIT.KRW-XVG&count=2&to=2018-04-27%2009:29:00")
//    @GET("/crix/candles/minutes/1")
//    Call<UpbitPriceResponse> getKrwCoinAnswers(@Query("code") String code, @Query("count") String count, @Query("to") String to);

//    https://crix-api-endpoint.upbit.com/v1/crix/candles/days?code=CRIX.UPBIT.KRW-SNT&count=2&to=2018-01-02%2000:00:00
    @GET("/v1/crix/candles/days")
    Call<List<UpbitPriceResponse>> getKrwCoinAnswers(@Query("code") String code, @Query("count") String count, @Query("to") String to);


    // https://crix-api-cdn.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-BTC&count=1&to=2018-05-27%2008:58:00
}