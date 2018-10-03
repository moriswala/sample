package com.company.mohammedyakub.ui.builddates;

import android.app.Application;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.ui.manufacturerdetaillist.ManufacturerDetailListAdapter;
import com.company.mohammedyakub.ui.manufacturerdetaillist.ManufacturerDetailViewModel;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class BuiltDatesListModule {

    @Provides
    BuiltDatesViewModel provideManufacturersViewModel(Application application , DataManager dataManager) {
        return new BuiltDatesViewModel(application , dataManager);
    }


    @Provides
    BuiltDatesListAdapter provideManufacturerListAdapter(){
        return new BuiltDatesListAdapter(new ArrayList<>());
    }
}
