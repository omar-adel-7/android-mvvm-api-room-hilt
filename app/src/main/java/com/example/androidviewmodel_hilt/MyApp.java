package com.example.androidviewmodel_hilt;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.hilt.android.HiltAndroidApp;
import okhttp3.OkHttpClient;

@HiltAndroidApp
public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // Adding an Network Interceptor for Debugging purpose :
        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                 .build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
        Gson gson = new GsonBuilder().create();
        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));


    }



}
