package com.example.androidviewmodel_hilt.data.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CacheApiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CacheApi> CacheApis);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CacheApi CacheApi);

    @Update
    int  update(CacheApi CacheApi);

    @Query("DELETE FROM CacheApi WHERE url = :url and params = :params and beanName = :beanName and objectOfArrayBeanName = :objectOfArrayBeanName")
      int deleteByCustom(String url , String params , String beanName,String objectOfArrayBeanName);

    @Delete
    int  delete(CacheApi CacheApi);

    @Query("SELECT * FROM CacheApi")
     LiveData<List<CacheApi>> getAll();

    @Query("SELECT * FROM CacheApi where url = :url and params = :params ")
    LiveData<CacheApi> getObject(String url,String params);

    @Query("SELECT * FROM CacheApi where url = :url and params = :params ")
     CacheApi  getObjectSync(String url,String params);

    @Query("SELECT * FROM CacheApi")
     List<CacheApi> getAllSync();
}
