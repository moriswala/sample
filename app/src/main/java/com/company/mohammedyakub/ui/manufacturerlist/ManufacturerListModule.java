package com.company.mohammedyakub.ui.manufacturerlist;

import android.app.Application;

import com.company.mohammedyakub.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class ManufacturerListModule {

    @Provides
    ManufacturerViewModel provideManufacturersViewModel(Application application , DataManager dataManager) {
        return new ManufacturerViewModel(application , dataManager);
    }


    @Provides
    ManufacturerListAdapter provideManufacturerListAdapter(){
        return new ManufacturerListAdapter();
    }
}
