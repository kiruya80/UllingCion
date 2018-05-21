package com.ulling.ullingcion.network;

import com.ulling.ullingcion.entites.Cryptowat.CryptoWatchCandles;
import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.LineMsgResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by P100651 on 2017-07-04.
 * <p>
 * https://futurestud.io/tutorials/retrofit-2-add-multiple-query-parameter-with-querymap
 * <p>
 * https://stackoverflow.com/questions/36730086/retrofit-2-url-query-parameter
 */
public interface CryptowatApi {

    @GET("markets/bitfinex/btcusd/summary")
    Call<CryptowatSummary> getSummary();

    //    @GET("markets/bitfinex/btcusd/ohlc?periods=604800&after=1483196400")
    @GET("markets/bitfinex/btcusd/ohlc")
//    Call<CryptoWatchCandles> getCandlesStick(@Query("before") long before, @Query("after") long after, @Query("periods") int periods);
    Call<CryptoWatchCandles> getCandlesStick( @Query("after") long after, @Query("periods") int periods);


}