package com.company.mohammedyakub.ui.manufacturerdetaillist;

import android.app.Application;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.ui.manufacturerdetaillist.ManufacturerDetailListAdapter;
import com.company.mohammedyakub.ui.manufacturerdetaillist.ManufacturerDetailViewModel;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class ManufacturerDetailListModule {

    @Provides
    ManufacturerDetailViewModel provideManufacturersViewModel(Application application , DataManager dataManager) {
        return new ManufacturerDetailViewModel(application , dataManager);
    }


    @Provides
    ManufacturerDetailListAdapter provideManufacturerListAdapter(){
        return new ManufacturerDetailListAdapter(new ArrayList<>());
    }
}
