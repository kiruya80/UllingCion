package com.ulling.ullingcion.network;

import com.ulling.ullingcion.entites.Cryptowat.CryptowatSummary;
import com.ulling.ullingcion.entites.LineMsgResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by P100651 on 2017-07-04.
 *
 * https://futurestud.io/tutorials/retrofit-2-add-multiple-query-parameter-with-querymap
 *
 * https://stackoverflow.com/questions/36730086/retrofit-2-url-query-parameter
 *
 */
public interface CryptowatApi {

    @GET("markets/bitfinex/btcusd/summary")
    Call<CryptowatSummary> getSummary();

}