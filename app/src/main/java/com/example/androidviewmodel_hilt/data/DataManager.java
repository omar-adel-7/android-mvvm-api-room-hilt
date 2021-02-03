package com.example.androidviewmodel_hilt.data;


import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.androidviewmodel_hilt.R;
import com.example.androidviewmodel_hilt.data.Remote.Retrofit.RetrofitApiError;
import com.example.androidviewmodel_hilt.data.Remote.Retrofit.RetrofitApiHelper;
import com.example.androidviewmodel_hilt.data.beans.CitiesResponse;
import com.example.androidviewmodel_hilt.data.local.CacheApi;
import com.example.androidviewmodel_hilt.data.local.CacheApiDatabase;
import com.general.ui.utils.CustomProgressBar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidviewmodel_hilt.data.Remote.Retrofit.RetrofitApiHelper.API_GET_CERTAIN_COUNTRY;
import static com.example.androidviewmodel_hilt.data.Remote.Retrofit.RetrofitApiHelper.API_GET_CERTAIN_COUNTRY_PARAM;
import static com.example.androidviewmodel_hilt.data.Remote.Retrofit.RetrofitApiHelper.API_GET_CITIES_URL;
import static com.example.androidviewmodel_hilt.data.Remote.Retrofit.RetrofitApiHelper.BASE_URL;
import static com.example.androidviewmodel_hilt.data.local.CacheApi.insertInCache;


public class DataManager implements RetrofitApiError {


    @Inject
    public RetrofitApiHelper retrofitApiHelper;
    @Inject
    CacheApiDatabase cacheApiDatabase;
    @Inject
    CustomProgressBar customProgressBar;

    // error if use this constructor
//    @Inject
//    public DataManager( ) {
//        retrofitApiHelper.setRetrofitApiError(this);
//    }

     @Inject
    public DataManager(RetrofitApiHelper retrofitApiHelper) {
        this.retrofitApiHelper=retrofitApiHelper;
        retrofitApiHelper.setRetrofitApiError(this);
    }


    public LiveData<CitiesResponse> getCitiesRetrofit(AppCompatActivity appCompatActivity, boolean hasCache) {

        beforeNetworkCall(appCompatActivity);
        MutableLiveData<CitiesResponse> citiesResponseLiveData = new MutableLiveData<>();
        HashMap<String, Object> jsonParams = new HashMap();
        jsonParams.put(API_GET_CERTAIN_COUNTRY, API_GET_CERTAIN_COUNTRY_PARAM);
        RequestBody body = RequestBody.create(
                (new JSONObject(jsonParams)).toString()
                , okhttp3.MediaType.parse("application/json; charset=utf-8"));

        if (checkInternet(appCompatActivity)) {
            Call<CitiesResponse> call = retrofitApiHelper.getAPIService(appCompatActivity).getCities(body);
            call.enqueue(new Callback<CitiesResponse>() {
                @Override
                public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                    afterNetworkCall(appCompatActivity);
                    if (response.isSuccessful()) {
                        if (hasCache) {
                            insertInCache(cacheApiDatabase, API_GET_CITIES_URL, jsonParams.toString(), response.body().getClass().getName()
                                    , null, new Gson().toJson(response.body()));
                        }
                        //  citiesResponseLiveData.setValue(response.body());//correct
                        citiesResponseLiveData.postValue(response.body());//correct
                    } else {

                        try {
                            Gson gson = new Gson();
                            CitiesResponse citiesResponse = gson.fromJson(response.errorBody().string(), CitiesResponse.class);
                            onResponseError(appCompatActivity, citiesResponse.getMsg());
                        } catch (IOException e) {
                            e.printStackTrace();
                            onResponseError(appCompatActivity, "");
                        }
                    }

                }

                @Override
                public void onFailure(Call<CitiesResponse> call, Throwable t) {
                    onResponseError(appCompatActivity, t.getMessage());
                }
            });

        } else {
            noNetworkConnection(appCompatActivity);
            if (hasCache) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CacheApi cacheApi = cacheApiDatabase.cacheApiDao().getObjectSync(API_GET_CITIES_URL
                                , jsonParams.toString());
                        if (cacheApi != null
                        ) {
                            citiesResponseLiveData.postValue((CitiesResponse) CacheApi.loadDataFromCache(cacheApi));
                            afterNetworkCall(appCompatActivity);
                        }
                    }
                }).start();
            }

        }
        return citiesResponseLiveData;
    }


    public LiveData<CitiesResponse> getCitiesFastNetworking(AppCompatActivity appCompatActivity, boolean hasCache) {

        beforeNetworkCall(appCompatActivity);
        MutableLiveData<CitiesResponse> citiesResponseLiveData = new MutableLiveData<>();
        HashMap<String, Object> jsonParams = new HashMap();
        jsonParams.put(API_GET_CERTAIN_COUNTRY, API_GET_CERTAIN_COUNTRY_PARAM);
        if (checkInternet(appCompatActivity)) {
            AndroidNetworking.post(BASE_URL+API_GET_CITIES_URL)
                    .addBodyParameter(API_GET_CERTAIN_COUNTRY, API_GET_CERTAIN_COUNTRY_PARAM)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsObject(CitiesResponse.class, new ParsedRequestListener<CitiesResponse>() {

                        @Override
                        public void onResponse(CitiesResponse response) {
                            afterNetworkCall(appCompatActivity);
                            if (hasCache) {
                                insertInCache(cacheApiDatabase, API_GET_CITIES_URL,
                                        jsonParams.toString(), response.getClass().getName()
                                        , null, new Gson().toJson(response));
                            }
                            // citiesResponseLiveData.setValue(response);//correct
                            citiesResponseLiveData.postValue(response);//correct
                        }

                        @Override
                        public void onError(ANError anError) {
                            if(anError.getErrorBody()==null)
                            {
                                onResponseError(appCompatActivity, anError.getErrorDetail());
                            }
                            else
                            {
                                Gson gson = new Gson();
                                CitiesResponse citiesResponse = gson.fromJson(anError.getErrorBody(), CitiesResponse.class);
                                onResponseError(appCompatActivity, citiesResponse.getMsg());
                            }

                        }
                    });


        } else {
            noNetworkConnection(appCompatActivity);
            if (hasCache) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CacheApi cacheApi = cacheApiDatabase.cacheApiDao().getObjectSync(API_GET_CITIES_URL
                                , jsonParams.toString());
                        if (cacheApi != null
                        ) {
                            citiesResponseLiveData.postValue((CitiesResponse) CacheApi.loadDataFromCache(cacheApi));
                            afterNetworkCall(appCompatActivity);
                        }
                    }
                }).start();
            }
        }
        return citiesResponseLiveData;
    }

    @Override
    public void onInternetUnavailable(AppCompatActivity appCompatActivity, Request request) {
        Log.e("url", request.url().url().toString());
        afterNetworkCall(appCompatActivity);
    }

    @Override
    public void onResponseError(AppCompatActivity appCompatActivity, String errorMessage) {
        afterNetworkCall(appCompatActivity);
        Log.e("error", errorMessage);
        if (errorMessage.isEmpty()) {
            errorResponse(appCompatActivity);
        } else {
            errorResponse(appCompatActivity, errorMessage);
        }
    }

    public static boolean checkInternet(AppCompatActivity appCompatActivity) {
        ConnectivityManager cm = (ConnectivityManager) appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    private void beforeNetworkCall(AppCompatActivity appCompatActivity) {
        customProgressBar.show(appCompatActivity,
                true, null, appCompatActivity.getString(R.string.wait), false, null,
                false, 0, R.style.MyProgressDialogStyle);
    }

    private void afterNetworkCall(AppCompatActivity appCompatActivity) {
        customProgressBar.dismissProgress(appCompatActivity);
    }

    private void noNetworkConnection(AppCompatActivity appCompatActivity) {
        appCompatActivity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(appCompatActivity, appCompatActivity.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            }
        });

        customProgressBar.dismissProgress(appCompatActivity);
    }

    private void errorResponse(AppCompatActivity appCompatActivity, String errorMessage) {
        appCompatActivity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(appCompatActivity, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        customProgressBar.dismissProgress(appCompatActivity);
    }

    private void errorResponse(AppCompatActivity appCompatActivity) {
        appCompatActivity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(appCompatActivity, appCompatActivity.getString(R.string.response_error), Toast.LENGTH_LONG).show();
            }
        });

        customProgressBar.dismissProgress(appCompatActivity);
    }

}
