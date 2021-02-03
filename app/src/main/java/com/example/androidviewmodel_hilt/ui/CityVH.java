package com.example.androidviewmodel_hilt.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidviewmodel_hilt.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CityVH extends RecyclerView.ViewHolder {


    View itemView;
    Context context;
    @BindView(R.id.txtvCity)
    TextView txtvCity;



    public CityVH(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bindData(final Object item, final int position) {

        final String cityName = (String) item;
        txtvCity.setText(cityName);
    }

    public static View getView(Context context, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.row_city, viewGroup, false);
    }


}


