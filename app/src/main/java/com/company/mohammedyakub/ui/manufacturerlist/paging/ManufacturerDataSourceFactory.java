package com.company.mohammedyakub.ui.manufacturerlist.paging;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.Manufacturer;

import io.reactivex.disposables.CompositeDisposable;

public class ManufacturerDataSourceFactory extends DataSource.Factory {

    private final DataManager dataManager;
    private final Application context;
    private final CompositeDisposable compositDisposable;
    private MutableLiveData<PageKeyedDataSource<Integer, Manufacturer>> itemLiveDataSource = new MutableLiveData<>();

    public ManufacturerDataSourceFactory(Application context, DataManager dataManager,
                                         CompositeDisposable compositeDisposable) {
        this.context = context;
        this.dataManager = dataManager;
        this.compositDisposable = compositeDisposable;

    }


    @Override
    public DataSource<Integer, Manufacturer> create() {
        ManufacturerDataSource itemDataSource = new ManufacturerDataSource(context, dataManager, compositDisposable);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Manufacturer>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
