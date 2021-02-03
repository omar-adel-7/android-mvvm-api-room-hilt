package com.example.androidviewmodel_hilt.data.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesResponse {

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private List<String> data ;

    @SerializedName("error")
    private boolean error;

    public String getMsg() {
        return msg;
    }

    public List<String> getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }
}
