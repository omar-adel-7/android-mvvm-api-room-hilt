package com.example.androidviewmodel_hilt.data.Repositories;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.androidviewmodel_hilt.data.DataManager;
import com.example.androidviewmodel_hilt.data.beans.CitiesResponse;

import javax.inject.Inject;

public class CitiesRepository {

    @Inject
    DataManager dataManager;

    @Inject
    public CitiesRepository() {
    }

    public LiveData<CitiesResponse> getCities(AppCompatActivity appCompatActivity, boolean hasCache) {
        return dataManager.getCitiesRetrofit(appCompatActivity, hasCache);
        //return dataManager.getCitiesFastNetworking(appCompatActivity,hasCache);
    }

}
