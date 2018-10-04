package com.company.mohammedyakub.ui.manufacturerdetaillist;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.model.ManufacturerItems;
import com.company.mohammedyakub.ui.Base.BaseViewModel;
import com.company.mohammedyakub.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ManufacturerDetailViewModel extends BaseViewModel {

    private static final String TAG = ManufacturerDetailViewModel.class.getSimpleName();

    MutableLiveData<List<ManufacturerItems>> manufacturerDetailLiveData;

    @Inject
    public ManufacturerDetailViewModel(Application context, DataManager dataManager) {
        super(context, dataManager);
    }

    /**
     * fetch manufacturers from server OR local database based on network.
     *
     * if internet available then load manufacturers from server and update database
     * else fetch manufacturers from database
     * @param code
     */
    void fetchManufacturerList(String code){
        if(isNetworkConnected()){
            fetchManufacturerItemsFromServer(code);
        }else{
            fetchManufacturerItemsFromDB(code);
        }
    }


    /**
     * fetch manufacturers from local database
     *
     * @param code
     */
    private void fetchManufacturerItemsFromDB(String code){
        getCompositeDisposable().add(getDataManager().loadAllManufacturerItems(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(manufacturerItems->{
                    // notify subscribers about the loaded data
                    manufacturerDetailLiveData.setValue(manufacturerItems);
                } , throwable -> {
                    // notify subscribers about the error msg
                    getErrorMsg().setValue(throwable.getMessage());
                }));
    }



    /**
     * fetch manufacturers from remote server
     *
     * @param code
     */
    private void fetchManufacturerItemsFromServer(String code){
        // show loading
        showLoading.call();
        Disposable s = getDataManager().fetchManufacturerItemListOfManufacturerCode(code,
                AppConstants.API_KEY,  0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // hide loading dialog
                    hideLoading.call();
                    List<ManufacturerItems> list = mapToList(code, response.getWkda());
                    // update manufacturers in db
                    insertManufacturers(list);
                    // notify subscribers about the new loaded data
                    manufacturerDetailLiveData.setValue(list);
                }, throwable -> {
                    // hide loading dialog
                    hideLoading.call();
                    // notify subscribers about the error msg
                    getErrorMsg().setValue(throwable.getMessage());
                });
        getCompositeDisposable().add(s);
    }

    private List<ManufacturerItems> mapToList(String manufacturerCode, Map<String, String> map){
        List manuList = new ArrayList<ManufacturerItems>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String code = entry.getKey();
            String name = entry.getValue();
            manuList.add(new ManufacturerItems(manufacturerCode, code, name));
        }
        return manuList;
    }

    /**
     * insert manufacturers in database
     *
     * @param manufacturerItems
     */
    private void insertManufacturers(List<ManufacturerItems> manufacturerItems){
        getCompositeDisposable().add(getDataManager().saveManufacturerItems(manufacturerItems)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    Log.d(TAG , manufacturerItems.size()+" manufacturers inserted in db");
                } , throwable -> {
                    Log.d(TAG , "error inserting manufacturers : " +throwable.getMessage());
                }));
    }



    public MutableLiveData<List<ManufacturerItems>> getManufacturerLiveData() {
        if(manufacturerDetailLiveData ==null)
            manufacturerDetailLiveData = new MutableLiveData<List<ManufacturerItems>>();
        return manufacturerDetailLiveData;
    }
}
