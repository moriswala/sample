package com.company.mohammedyakub.data;

import com.company.mohammedyakub.data.local.db.DbHelper;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.model.ManufacturerItems;
import com.company.mohammedyakub.data.remote.ApiService;
import com.company.mohammedyakub.data.remote.api.ManufacturerListResponce;
import com.company.mohammedyakub.data.local.prefs.SharedPreferenceUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 *
 * this class built to provide single point of access to application data sources ( shared Pref - Api Service )
 *
 */
@Singleton
public class AppDataManager implements DataManager {

    DbHelper mDbHelper;
    ApiService mApiService;
    SharedPreferenceUtils mSharedPreferenceUtils;

    @Inject
    public AppDataManager(ApiService mApiService, DbHelper mDbHelper, SharedPreferenceUtils mSharedPreferenceUtils) {
        this.mApiService = mApiService;
        this.mSharedPreferenceUtils = mSharedPreferenceUtils;
        this.mDbHelper = mDbHelper;
    }


    /****************************   API METHODS  **************************************/


    /**
     * fetch Manufacturers from server by selected sort by code.
     *
     * @param api_key
     * @return
     */
    @Override
    public Observable<ManufacturerListResponce> fetchManufacturersList(String api_key, Integer page, Integer pageSize) {
        return mApiService.fetchManufacturersList(api_key , page, pageSize);
    }

    @Override
    public Observable<ManufacturerListResponce> fetchManufacturerItemListOfManufacturerCode(
            String manufacturer,
            String api_key,
            Integer page,
            Integer pageSize){
        return mApiService.fetchManufacturerItemListOfManufacturerCode(manufacturer,
                api_key, page, pageSize);
    }


    /******************************   DATABASE METHODS  *************************************/


    /**
     * insert all manufacturers in database
     *
     * @param manufacturers
     * @return
     */
    @Override
    public Observable<Boolean> saveManufacturers(List<Manufacturer> manufacturers) {
        return mDbHelper.saveManufacturers(manufacturers);
    }



    /**
     * select all manufacturers from database
     *
     * @return
     */
    @Override
    public Observable<List<Manufacturer>> loadAllManufacturers() {
        return mDbHelper.loadAllManufacturers();
    }

    @Override
    public Observable<Boolean> saveManufacturerItems(List<ManufacturerItems> manufacturerItems) {
        return mDbHelper.saveManufacturerItems(manufacturerItems);
    }

    @Override
    public Observable<List<ManufacturerItems>> loadAllManufacturerItems(String code) {
        return mDbHelper.loadAllManufacturerItems(code);
    }


}
