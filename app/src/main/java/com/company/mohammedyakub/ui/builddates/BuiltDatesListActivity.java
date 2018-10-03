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

    @BindView(R.id.activity_main_rv) RecyclerView movies_recycler_view;

    @Inject
    BuiltDatesViewModel builtDatesViewModel;

    @Inject
    BuiltDatesListAdapter builtDatesListAdapter;

    @Override
    public BuiltDatesViewModel getViewModel() {
        return builtDatesViewModel;
    }

    public static Intent getStartIntent(Context context, String manufacturerCode, String typeCode){
        Intent intent = new Intent(context, BuiltDatesListActivity.class);
        intent.putExtra(KEY_CODE, manufacturerCode);
        intent.putExtra(KEY_TYPE, typeCode);
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
                && getIntent().hasExtra(KEY_TYPE) && !TextUtils.isEmpty(getIntent().getStringExtra(KEY_TYPE)))
        {
            String code = (String)getIntent().getStringExtra(KEY_CODE);
            String typeCode = (String)getIntent().getStringExtra(KEY_TYPE);
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
        movies_recycler_view.setLayoutManager(mLayoutManager);
        movies_recycler_view.setAdapter(builtDatesListAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        builtDatesListAdapter = null;
    }
}
