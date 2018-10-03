package com.company.mohammedyakub.ui.manufacturerlist;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.company.mohammedyakub.R;
import com.company.mohammedyakub.ui.Base.BaseActivity;


import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ManufacturerListActivity extends BaseActivity<ManufacturerViewModel>{

    @BindView(R.id.activity_main_rv) RecyclerView movies_recycler_view;

    @Inject
    ManufacturerViewModel moviesViewModel;

    @Inject
    ManufacturerListAdapter movieListAdapter;

    @Override
    public ManufacturerViewModel getViewModel() {
        return moviesViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // initialize manufacturers recycler view
        setupManufacturersRecyclerView();

        // start fetching manufacturers based on sort type
        moviesViewModel.fetchManufacturerList();

        // subscribe to manufacturers live data changes
        moviesViewModel.getManufacturerLiveData().observe(this, manufacturers -> {
            if(manufacturers!=null)
               movieListAdapter.addItems(new ArrayList<>(manufacturers));
        });
    }


    private void setupManufacturersRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        movies_recycler_view.setLayoutManager(mLayoutManager);
        movies_recycler_view.setAdapter(movieListAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieListAdapter = null;
    }
}
