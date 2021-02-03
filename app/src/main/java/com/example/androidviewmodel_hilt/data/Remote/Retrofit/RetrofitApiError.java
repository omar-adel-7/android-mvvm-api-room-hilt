package com.example.androidviewmodel_hilt.data.Remote.Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Request;

public interface RetrofitApiError {
    void onInternetUnavailable(AppCompatActivity appCompatActivity ,   Request request);
    void onResponseError(AppCompatActivity appCompatActivity , String errorMessage );

}