package com.example.androidviewmodel_hilt.data.local;


import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by guendouz on 15/02/2018.
 */

@Database(entities = {CacheApi.class}, version = 1  , exportSchema = false)
public abstract class CacheApiDatabase extends RoomDatabase {

    public abstract CacheApiDao cacheApiDao();

}
