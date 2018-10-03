package com.company.mohammedyakub.data.local.db;

import android.util.Log;

import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.model.ManufacturerItems;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper{

    private static final String TAG = AppDbHelper.class.getSimpleName();

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<Boolean> saveManufacturers(List<Manufacturer> manufacturers) {
        return Observable.fromCallable(() -> {


            try{
                mAppDatabase.manufacturerDao().insertAll(manufacturers);
                return true;
            }catch (Exception e){
                Log.d(TAG , e.getMessage());
            }

            return false;
        });
    }



    @Override
    public Observable<List<Manufacturer>> loadAllManufacturers() {
        return Observable.fromCallable(() -> mAppDatabase.manufacturerDao().loadAll());
    }

    @Override
    public Observable<Boolean> saveManufacturerItems(List<ManufacturerItems> manufacturerItems) {
        return Observable.fromCallable(() -> {
            try{
                mAppDatabase.manufacturerItemDao().insertAll(manufacturerItems);
                return true;
            }catch (Exception e){
                Log.d(TAG , e.getMessage());
            }
            return false;
        });
    }

    @Override
    public Observable<List<ManufacturerItems>> loadAllManufacturerItems(String code) {
        return Observable.fromCallable(() -> mAppDatabase.manufacturerItemDao().loadAllWithManufacturerCode(code));
    }
}
