package com.general.base_act_frg;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;


public abstract class BaseAppCompatActivity
        extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        configureUI();
    }

    protected abstract int getLayoutResource();
    protected abstract void configureUI();


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
