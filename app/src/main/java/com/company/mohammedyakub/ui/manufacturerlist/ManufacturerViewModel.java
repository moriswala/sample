package com.company.mohammedyakub.ui.manufacturerlist;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.ui.Base.BaseViewModel;
import com.company.mohammedyakub.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ManufacturerViewModel extends BaseViewModel {

    private static final String TAG = ManufacturerViewModel.class.getSimpleName();

    MutableLiveData<List<Manufacturer>> manufacturerLiveData;

    @Inject
    public ManufacturerViewModel(Application context, DataManager dataManager) {
        super(context, dataManager);
    }

    /**
     * fetch manufacturers from server OR local database based on network.
     *
     * if internet available then load manufacturers from server and update database
     * else fetch manufacturers from database
     */
    void fetchManufacturerList(){
        if(isNetworkConnected()){
            fetchManufacturersFromServer();
        }else{
            fetchManufacturersFromDB();
        }
    }


    /**
     * fetch manufacturers from local database
     *
     */
    private void fetchManufacturersFromDB(){
        getCompositeDisposable().add(getDataManager().loadAllManufacturers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(manufacturers->{
                    // notify subscribers about the loaded data
                    manufacturerLiveData.setValue(manufacturers);
                } , throwable -> {
                    // notify subscribers about the error msg
                    getErrorMsg().setValue(throwable.getMessage());
                }));
    }



    /**
     * fetch manufacturers from remote server
     *
     */
    private void fetchManufacturersFromServer(){
        // show loading
        showLoading.call();

        Disposable s = getDataManager().fetchManufacturersList(AppConstants.API_KEY, 0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // hide loading dialog
                    hideLoading.call();

                    List<Manufacturer> list = mapToList(response.getWkda());
                    // update manufacturers in db
                    insertManufacturers(list);

                    // notify subscribers about the new loaded data
                    manufacturerLiveData.setValue(list);

                }, throwable -> {
                    // hide loading dialog
                    hideLoading.call();

                    // notify subscribers about the error msg
                    getErrorMsg().setValue(throwable.getMessage());
                });
        getCompositeDisposable().add(s);
    }

    private List<Manufacturer> mapToList(Map<String, String> map){
        List manuList = new ArrayList<Manufacturer>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String code = entry.getKey();
            String name = entry.getValue();
            manuList.add(new Manufacturer(code, name));
        }
        return manuList;
    }

    /**
     * insert manufacturers in database
     *
     * @param manufacturers
     */
    private void insertManufacturers(List<Manufacturer> manufacturers){
        getCompositeDisposable().add(getDataManager().saveManufacturers(manufacturers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    Log.d(TAG , manufacturers.size()+" manufacturers inserted in db");
                } , throwable -> {
                    Log.d(TAG , "error inserting manufacturers : " +throwable.getMessage());
                }));
    }



    public MutableLiveData<List<Manufacturer>> getManufacturerLiveData() {
        if(manufacturerLiveData ==null)
            manufacturerLiveData = new MutableLiveData<List<Manufacturer>>();
        return manufacturerLiveData;
    }
}
