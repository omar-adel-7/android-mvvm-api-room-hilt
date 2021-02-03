package com.example.androidviewmodel_hilt.di;

import android.content.Context;

import androidx.room.Room;

import com.example.androidviewmodel_hilt.data.local.CacheApiDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
 @Module
public class MyModule {

//     @Provides
//     @Singleton
//     static CitiesRepository providesCitiesRepository(    )
//     {
//         return new CitiesRepository( );
//     }

//     @Provides
//    @Singleton
//    static DataManager providesDataManager(    )
//    {
//     return new DataManager( );
//    }


    //  provides annotation in module for RetrofitApiHelper , use it or use constructor injection  for RetrofitApiHelper
    //  or both if you want but of course not necessary
   // @Provides
//    @Singleton
//    static RetrofitApiHelper providesRetrofitApiHelper()
//    {
//        return new RetrofitApiHelper();
//    }


     @Provides
    @Singleton
    static CacheApiDatabase providesCacheApiDatabase(@ApplicationContext Context context)
    {
        return Room.databaseBuilder(context.getApplicationContext(),
                CacheApiDatabase.class, "CacheApis.db")
                // using main thread only for updateCacheApi and query CacheApis in CacheApiManagerUtil
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
