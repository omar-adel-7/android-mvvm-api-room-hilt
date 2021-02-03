package com.example.androidviewmodel_hilt.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidviewmodel_hilt.data.Repositories.CitiesRepository;
import com.example.androidviewmodel_hilt.data.beans.CitiesResponse;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class CitiesViewModel extends AndroidViewModel  {


    @Inject
    public CitiesViewModel(@NonNull Application application
                           // important make dependencies here in constructor , this as you write
                           // @Inject CitiesRepository citiesRepository;
                           // and then it goes into any dependency here and also call constructor of  any injected dependency in it
                           , CitiesRepository citiesRepository
      ) {
        super(application);
         this.citiesRepository= citiesRepository;
      }

    // @Inject
    CitiesRepository citiesRepository;
    public LiveData<CitiesResponse> getCities(AppCompatActivity appCompatActivity  , boolean hasCache ) {
        return citiesRepository.getCities(appCompatActivity,hasCache);
    }


}
