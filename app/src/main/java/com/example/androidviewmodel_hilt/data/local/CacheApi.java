package com.example.androidviewmodel_hilt.data.local;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.util.Date;

@Entity
public class CacheApi implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private long id;

    private String url;
    private String params;
    private String beanName;
    private String objectOfArrayBeanName;
    private long date;
    private String response;


    public CacheApi() {
    }

    public CacheApi(Long id) {
        this.id = id;
    }

    public CacheApi(Long id, String url, String params, String beanName, String objectOfArrayBeanName
            , long date, String response) {
        this.id = id;
        this.url = url;
        this.params = params;
        this.beanName = beanName;
        this.objectOfArrayBeanName = objectOfArrayBeanName;
        this.date = date;
        this.response = response;
    }

    public CacheApi(String url, String params) {
        this.url = url;
        this.params = params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(params);
        parcel.writeString(beanName);
        parcel.writeString(objectOfArrayBeanName);
        parcel.writeLong(date);
        parcel.writeString(response);
    }

    protected CacheApi(Parcel in) {
        url = in.readString();
        params = in.readString();
        beanName = in.readString();
        objectOfArrayBeanName = in.readString();
        date = in.readLong();
        response = in.readString();
    }

    public static final Creator<CacheApi> CREATOR = new Creator<CacheApi>() {
        @Override
        public CacheApi createFromParcel(Parcel in) {
            return new CacheApi(in);
        }

        @Override
        public CacheApi[] newArray(int size) {
            return new CacheApi[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    public String getObjectOfArrayBeanName() {
        return objectOfArrayBeanName;
    }

    public void setObjectOfArrayBeanName(String objectOfArrayBeanName) {
        this.objectOfArrayBeanName = objectOfArrayBeanName;
    }

    public static void insertInCache(CacheApiDatabase cacheApiDatabase, String url, String params, String beanName, String objectOfArrayBeanName, String response) {
        cacheApiDatabase.cacheApiDao().deleteByCustom(url, params, beanName, objectOfArrayBeanName);
        CacheApi cacheApi = new CacheApi();
        cacheApi.setUrl(url);
        cacheApi.setParams(params);
        cacheApi.setBeanName(beanName);
        cacheApi.setObjectOfArrayBeanName(objectOfArrayBeanName);
        cacheApi.setResponse(response);
        if (objectOfArrayBeanName != null) {
            Log.e("objectOfArrayBeanName", objectOfArrayBeanName);
        }
        cacheApi.setDate(new Date().getTime());

        cacheApiDatabase.cacheApiDao().insert(cacheApi);
    }

    public static Object loadDataFromCache(CacheApi cacheApi) {
        String result = null;
        String beanName = null;

        if (cacheApi != null) {
            beanName = cacheApi.getBeanName();
            result = cacheApi.getResponse();
            try {
                Class type = Class.forName(beanName);
                Gson gson = new Gson();
                String objectOfArrayBeanName = cacheApi.getObjectOfArrayBeanName();
                Object ob = null;
                if (objectOfArrayBeanName == null) {
                    ob = (gson.fromJson(result, type));
                }
//                else
//                {
//                    if(objectOfArrayBeanName.equals(CertainBean.class.getName()))
//                    {
//                        Type listType = new TypeToken<ArrayList<CertainBean>>() {}.getType();
//                        ob = (gson.fromJson(result, listType));
//                    }
//                }

                return ob;
            } catch (Exception ex) {
                Log.e("ExceptionLoadDataFromCache", ex.toString());
            }
        }

        return null;
    }
}
