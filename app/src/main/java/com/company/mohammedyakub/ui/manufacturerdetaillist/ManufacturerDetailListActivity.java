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
    
    @BindView(R.id.activity_main_rv) RecyclerView movies_recycler_view;

    @Inject
    ManufacturerDetailViewModel manufacturerDetailViewModel;

    @Inject
    ManufacturerDetailListAdapter manufacturerDetailListAdapter;

    @Override
    public ManufacturerDetailViewModel getViewModel() {
        return manufacturerDetailViewModel;
    }

    public static Intent getStartIntent(Context context, String mCode){
        Intent intent = new Intent(context, ManufacturerDetailListActivity.class);
        intent.putExtra(KEY_CODE, mCode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // initialize manufacturers recycler view
        setupManufacturersRecyclerView();
        if(getIntent().hasExtra(KEY_CODE) && !TextUtils.isEmpty(getIntent().getStringExtra(KEY_CODE)))
        {
            String code = (String)getIntent().getStringExtra(KEY_CODE);
            // start fetching manufacturers based on sort type
            manufacturerDetailViewModel.fetchManufacturerList(getIntent().getStringExtra(KEY_CODE));

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
