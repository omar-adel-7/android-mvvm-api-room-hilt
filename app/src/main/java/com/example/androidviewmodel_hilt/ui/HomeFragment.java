package com.example.androidviewmodel_hilt.ui;

import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidviewmodel_hilt.R;
import com.example.androidviewmodel_hilt.data.beans.CitiesResponse;
import com.general.base_act_frg.BaseSupportFragment;
import com.general.ui.adapters.GenericRecyclerViewAdapter;

import butterknife.BindView;
import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeFragment extends BaseSupportFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private GenericRecyclerViewAdapter mAdapter;

    CitiesViewModel citiesViewModel ;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void configureUI() {
        citiesViewModel = new ViewModelProvider(this).get(CitiesViewModel.class);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContainerActivity()));
        mAdapter = new GenericRecyclerViewAdapter(getContainerActivity(), new GenericRecyclerViewAdapter.AdapterDrawData() {
            @Override
            public RecyclerView.ViewHolder getView(ViewGroup parent, int viewType) {

                return new CityVH(getContainerActivity(),
                        CityVH.getView(getContainerActivity(), parent));
            }

            @Override
            public void bindView(GenericRecyclerViewAdapter genericRecyclerViewAdapter,
                                 RecyclerView.ViewHolder holder, Object item, int position) {
                ((CityVH) holder).bindData(
                        genericRecyclerViewAdapter.getItem(position), position);
            }
        });
        recyclerview.setAdapter(mAdapter);


        citiesViewModel.getCities(getContainerActivity(), true).observe(this, new Observer<CitiesResponse>() {
            @Override
            public void onChanged(@Nullable CitiesResponse citiesResponse) {
                if (citiesResponse != null) {
                    if(citiesResponse.isError())
                    {
                        Toast.makeText(getContainerActivity(),getString(R.string.app_name),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        mAdapter.setAll(citiesResponse.getData());
                    }
                }
            }
        });


    }
}
