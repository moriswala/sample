package com.company.mohammedyakub.di.builder;

import com.company.mohammedyakub.ui.builddates.BuiltDatesListActivity;
import com.company.mohammedyakub.ui.builddates.BuiltDatesListModule;
import com.company.mohammedyakub.ui.manufacturerdetaillist.ManufacturerDetailListActivity;
import com.company.mohammedyakub.ui.manufacturerdetaillist.ManufacturerDetailListModule;
import com.company.mohammedyakub.ui.manufacturerlist.ManufacturerListActivity;
import com.company.mohammedyakub.ui.manufacturerlist.ManufacturerListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ManufacturerListModule.class)
    abstract ManufacturerListActivity bindManufacturerListActivity();

    @ContributesAndroidInjector(modules = ManufacturerDetailListModule.class)
    abstract ManufacturerDetailListActivity bindManufacturerDetailListActivity();

    @ContributesAndroidInjector(modules = BuiltDatesListModule.class)
    abstract BuiltDatesListActivity bindBuiltDatesListActivity();
}
