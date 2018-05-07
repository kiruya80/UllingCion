package com.ulling.ullingcion.network;

import com.ulling.lib.core.network.QcBaseRetrofitService;
import com.ulling.lib.core.util.QcLog;
import com.ulling.ullingcion.common.ApiUrl;
import com.ulling.ullingcion.common.QcDefine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitUpbitService extends QcBaseRetrofitService {

    private static UpbitApi apiInterface;

    public synchronized static UpbitApi getInstance() {
        if (apiInterface == null) {
            apiInterface = provideApiService();
        }
        return apiInterface;
    }

    public void onDestroy() {
        apiInterface = null;
    }

    private static UpbitApi provideApiService() {
        return getProvideRetrofit(ApiUrl.BASE_UPBIT_URL, provideClient(true)).create(UpbitApi.class);
    }

    private static OkHttpClient provideClient(final boolean addParameter) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(QcDefine.DEBUG_FLAG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);


        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url;

                if (addParameter) {
//                    QUllingApplication appData = (QUllingApplication) context.getApplicationContext();

                    /* 공통 parameters */
//                        url = request.url().newBuilder()
//                                .addQueryParameter(APIConstants.APIKEY, appData.getApiKey())
//                                .addQueryParameter(APIConstants.CREDENTIAL, appData.getCredential())
//                                .addQueryParameter(APIConstants.DEVICE, appData.getDevice())
//                                .addQueryParameter(APIConstants.PARTNER, appData.getPartner())
//                                .addQueryParameter(APIConstants.POOQZONE, APIConstants.NONE)
//                                .addQueryParameter(APIConstants.REGION, appData.getRegion())
//                                .addQueryParameter(APIConstants.DRM, appData.getDrm().toLowerCase())
//                                .addQueryParameter(APIConstants.TARGETAGE, appData.getTargetAge())
//                                .build();

                    url = request.url().newBuilder().build();
                } else {
                    url = request.url().newBuilder().build();
                }
                request = request.newBuilder().url(url).build();
                QcLog.i("request url " + request.url());
                return chain.proceed(request);
            }
        })
                .connectTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor);

        return enableTlsOnPreLollipop(httpClientBuilder).build();
    }
}
