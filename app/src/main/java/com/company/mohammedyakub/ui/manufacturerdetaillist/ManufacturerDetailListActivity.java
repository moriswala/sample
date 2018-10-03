package com.company.mohammedyakub.ui.manufacturerdetaillist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.company.mohammedyakub.R;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.ui.Base.BaseActivity;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManufacturerDetailListActivity extends BaseActivity<ManufacturerDetailViewModel>{

    public static final String KEY_CODE = "code";
    private static final String KEY_NAME = "name";

    @BindView(R.id.activity_main_rv) RecyclerView movies_recycler_view;

    @Inject
    ManufacturerDetailViewModel manufacturerDetailViewModel;

    @Inject
    ManufacturerDetailListAdapter manufacturerDetailListAdapter;

    @Override
    public ManufacturerDetailViewModel getViewModel() {
        return manufacturerDetailViewModel;
    }

    public static Intent getStartIntent(Context context, String mCode, String name){
        Intent intent = new Intent(context, ManufacturerDetailListActivity.class);
        intent.putExtra(KEY_CODE, mCode);
        intent.putExtra(KEY_NAME, name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // initialize manufacturers recycler view
        setupManufacturersRecyclerView();
        if(getIntent().hasExtra(KEY_CODE) && !TextUtils.isEmpty(getIntent().getStringExtra(KEY_CODE))
                && getIntent().hasExtra(KEY_NAME) && !TextUtils.isEmpty(getIntent().getStringExtra(KEY_NAME)))
        {
            String code = (String)getIntent().getStringExtra(KEY_CODE);
            String name = (String)getIntent().getStringExtra(KEY_NAME);
            // start fetching manufacturers based on sort type
            manufacturerDetailViewModel.fetchManufacturerList(code);
            setTitle(name);
            // subscribe to manufacturers live data changes
            manufacturerDetailViewModel.getManufacturerLiveData().observe(this, manufacturerItems -> {
                if (manufacturerItems != null)
                    manufacturerDetailListAdapter.addItems(new ArrayList<>(manufacturerItems));
            });
        }
        else
            finish();
    }

    
    private void setupManufacturersRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        movies_recycler_view.setLayoutManager(mLayoutManager);
        movies_recycler_view.setAdapter(manufacturerDetailListAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manufacturerDetailListAdapter = null;
    }
}
