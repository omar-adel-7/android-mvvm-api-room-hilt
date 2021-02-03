package com.example.androidviewmodel_hilt.data.Remote.Retrofit;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public abstract  class  NetworkConnectionInterceptor implements Interceptor {


    public abstract boolean isInternetAvailable();

    public abstract void onInternetUnavailable(Request request);

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (!isInternetAvailable()) {
            onInternetUnavailable(request);
        }
        return chain.proceed(request);
    }


}
