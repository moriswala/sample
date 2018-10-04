package com.company.mohammedyakub.ui.manufacturerlist.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.Manufacturer;

public class ManufacturerDataSourceFactory extends DataSource.Factory {

    private final DataManager dataManager;
    private MutableLiveData<PageKeyedDataSource<Integer, Manufacturer>> itemLiveDataSource = new MutableLiveData<>();

    public ManufacturerDataSourceFactory(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public DataSource<Integer, Manufacturer> create() {
        ManufacturerDataSource itemDataSource = new ManufacturerDataSource(dataManager);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Manufacturer>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
