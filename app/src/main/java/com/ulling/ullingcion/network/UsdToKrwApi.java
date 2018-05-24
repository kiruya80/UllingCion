package com.ulling.ullingcion.network;

import com.ulling.ullingcion.entites.LineMsgResponse;
import com.ulling.ullingcion.entites.UpbitUsdToKrwResponse;

import java.util.List;
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
public interface UsdToKrwApi {

//    @FormUrlEncoded
    @GET("v1/forex/recent?codes=FRX.KRWUSD")
    Call<List<UpbitUsdToKrwResponse>> getUsdToKrw();

}