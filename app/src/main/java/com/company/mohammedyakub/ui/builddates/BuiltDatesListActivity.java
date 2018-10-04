package com.company.mohammedyakub.ui.builddates;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.company.mohammedyakub.R;
import com.company.mohammedyakub.ui.Base.BaseActivity;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuiltDatesListActivity extends BaseActivity<BuiltDatesViewModel>{

    public static final String KEY_CODE = "code";
    private static final String KEY_TYPE = "main-type";
    private static final String KEY_NAME = "name";

    @BindView(R.id.activity_main_rv) RecyclerView built_dates_recycler_view;

    @Inject
    BuiltDatesViewModel builtDatesViewModel;

    @Inject
    BuiltDatesListAdapter builtDatesListAdapter;

    @Override
    public BuiltDatesViewModel getViewModel() {
        return builtDatesViewModel;
    }

    public static Intent getStartIntent(Context context, String manufacturerCode, String typeCode,
                                        String typeName){
        Intent intent = new Intent(context, BuiltDatesListActivity.class);
        intent.putExtra(KEY_CODE, manufacturerCode);
        intent.putExtra(KEY_TYPE, typeCode);
        intent.putExtra(KEY_NAME, typeName);
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
                && getIntent().hasExtra(KEY_TYPE) && !TextUtils.isEmpty(getIntent().getStringExtra(KEY_TYPE))
                && getIntent().hasExtra(KEY_NAME) && !TextUtils.isEmpty(getIntent().getStringExtra(KEY_NAME)))
        {
            String code = (String)getIntent().getStringExtra(KEY_CODE);
            String typeCode = (String)getIntent().getStringExtra(KEY_TYPE);
            String typeName = (String)getIntent().getStringExtra(KEY_NAME);
            setTitle(typeName);
            // start fetching manufacturers based on sort type
            builtDatesViewModel.fetchBuilDatesList(code,
                    typeCode);

            // subscribe to manufacturers live data changes
            builtDatesViewModel.getBuiltDatesLiveData().observe(this, builtDates -> {
                if (builtDates != null)
                    builtDatesListAdapter.addItems(new ArrayList<>(builtDates));
            });
        }
        else
            finish();
    }

    
    private void setupManufacturersRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        built_dates_recycler_view.setLayoutManager(mLayoutManager);
        built_dates_recycler_view.setAdapter(builtDatesListAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        builtDatesListAdapter = null;
    }
}
