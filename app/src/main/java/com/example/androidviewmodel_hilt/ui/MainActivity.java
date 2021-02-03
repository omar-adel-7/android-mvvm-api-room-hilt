package com.example.androidviewmodel_hilt.ui;

import com.example.androidviewmodel_hilt.R;
import com.general.base_act_frg.BaseAppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;


 @AndroidEntryPoint
public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void configureUI() {

    }
}
