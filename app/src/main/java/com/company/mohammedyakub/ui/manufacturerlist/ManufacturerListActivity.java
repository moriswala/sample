package com.company.mohammedyakub.ui.manufacturerlist;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.company.mohammedyakub.R;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.ui.Base.BaseActivity;


import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ManufacturerListActivity extends BaseActivity<ManufacturerViewModel>{

    @BindView(R.id.activity_main_rv) RecyclerView manufacturer_list_recycler_view;

    @Inject
    ManufacturerViewModel manufacturerViewModel;

    @Inject
    ManufacturerListAdapter manufacturerListAdapter;

    @Override
    public ManufacturerViewModel getViewModel() {
        return manufacturerViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // initialize manufacturers recycler view
        setupManufacturersRecyclerView();

        manufacturerViewModel.manufacturerLivePagedList.observe(this, new Observer<PagedList<Manufacturer>>() {
            @Override
            public void onChanged(@Nullable PagedList<Manufacturer> items) {
                manufacturerListAdapter.submitList(items);
            }
        });
    }


    private void setupManufacturersRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        manufacturer_list_recycler_view.setLayoutManager(mLayoutManager);
        manufacturer_list_recycler_view.setAdapter(manufacturerListAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manufacturerListAdapter = null;
    }
}
