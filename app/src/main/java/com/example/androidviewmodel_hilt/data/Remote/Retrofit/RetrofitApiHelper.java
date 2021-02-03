package com.example.androidviewmodel_hilt.data.Remote.Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.androidviewmodel_hilt.data.DataManager.checkInternet;


public class RetrofitApiHelper {

    public static String BASE_URL = "https://countriesnow.space/api/v0.1/countries/";
    public static String API_GET_CITIES_URL = "cities";
    public static String API_GET_CERTAIN_COUNTRY = "country";
    public static String API_GET_CERTAIN_COUNTRY_PARAM = "Egypt";
    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder okHttpClient = null;
    private RetrofitApiError retrofitApiError;


    //or method 2 by constructor injection  for RetrofitApiHelper
    //  constructor injection here for RetrofitApiHelper , use it or use  provides annotation in module for RetrofitApiHelper
    //  or both if you want but of course not necessary
   @Inject
    public RetrofitApiHelper(   )
    {

     }
    public   APIService getAPIService(AppCompatActivity appCompatActivity   ) {
        return getClient(appCompatActivity,BASE_URL).create(APIService.class);
    }

    public   OkHttpClient getAPIOkHttpClient( AppCompatActivity appCompatActivity ) {
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient.Builder();
                //okHttpClient.readTimeout(1, TimeUnit.SECONDS)
                okHttpClient.readTimeout(60, TimeUnit.SECONDS)
                //okHttpClient.connectTimeout(1, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

                okHttpClient.addInterceptor(new NetworkConnectionInterceptor() {
                    @Override
                    public boolean isInternetAvailable() {
                        return  checkInternet(appCompatActivity);
                    }

                    @Override
                    public void onInternetUnavailable(Request request) {
                        // we can broadcast this event to activity/fragment/service
                        // through LocalBroadcastReceiver or
                        // RxBus/EventBus
                        // also we can call our own interface method
                        // like this.
                        if (retrofitApiError != null) {
                            retrofitApiError.onInternetUnavailable(appCompatActivity,request);
                        }
                    }

                });
        }
        return okHttpClient.build();
    }




    public   Retrofit getClient(AppCompatActivity appCompatActivity  , String baseUrl) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getAPIOkHttpClient(appCompatActivity))
                    .build();

        }
        return retrofit;
    }

    public void setRetrofitApiError(RetrofitApiError listener) {
        retrofitApiError = listener;
    }

    public void removeApiError() {
        retrofitApiError = null;
    }




}
