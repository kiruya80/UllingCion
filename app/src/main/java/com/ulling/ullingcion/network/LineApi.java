package com.ulling.ullingcion.network;

import com.ulling.ullingcion.common.Define;
import com.ulling.ullingcion.entites.LineMsgResponse;
import com.ulling.ullingcion.entites.UpbitPriceResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by P100651 on 2017-07-04.
 *
 * https://futurestud.io/tutorials/retrofit-2-add-multiple-query-parameter-with-querymap
 *
 * https://stackoverflow.com/questions/36730086/retrofit-2-url-query-parameter
 *
 */
public interface LineApi {

    @FormUrlEncoded
    @POST("api/notify")
    Call<LineMsgResponse> sendMsgRequest(@FieldMap Map<String,String> body);

}