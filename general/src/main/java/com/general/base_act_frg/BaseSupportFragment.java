package com.general.base_act_frg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

public abstract class BaseSupportFragment
        extends Fragment {
    private View view;

    public AppCompatActivity mActivity;

    public AppCompatActivity getContainerActivity() {
         return mActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            mActivity = (AppCompatActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setRetainInstance(false);
        setView(inflater.inflate(getLayoutResource(), container, false));
        ButterKnife.bind(this, getView());
        return getView();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureUI();
    }

    protected abstract int getLayoutResource();
    protected abstract void configureUI();


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


}