package com.company.mohammedyakub.ui.builddates;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.BuiltDate;
import com.company.mohammedyakub.ui.Base.BaseViewModel;
import com.company.mohammedyakub.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BuiltDatesViewModel extends BaseViewModel {

    private static final String TAG = BuiltDatesViewModel.class.getSimpleName();

    MutableLiveData<List<BuiltDate>> builtDatesLiveData;

    @Inject
    public BuiltDatesViewModel(Application context, DataManager dataManager) {
        super(context, dataManager);
    }

    /**
     * fetch manufacturers from server OR local database based on network.
     *
     *       if internet available then load manufacturers from server and update database
     *       else fetch manufacturers from database
     * @param manufacturerCode
     * @param typeCode
     */
    void fetchBuilDatesList(String manufacturerCode, String typeCode){
        if(isNetworkConnected()){
            fetchManufacturerItemsBuiltDatesFromServer(manufacturerCode, typeCode);
        }else{
            fetchManufacturerItemsBuiltDatesFromDB(manufacturerCode, typeCode);
        }
    }


    /**
     * fetch manufacturers from local database
     *
     * @param manufacturerCode
     * @param typeCode
     */
    private void fetchManufacturerItemsBuiltDatesFromDB(String manufacturerCode, String typeCode){
        getCompositeDisposable().add(getDataManager().loadAllManufacturerItemsBuiltDates(manufacturerCode, typeCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(builtDates->{
                    // notify subscribers about the loaded data
                    builtDatesLiveData.setValue(builtDates);
                } , throwable -> {
                    // notify subscribers about the error msg
                    getErrorMsg().setValue(throwable.getMessage());
                }));
    }


    /**
     * fetch manufacturers from remote server
     *
     * @param manufacturerCode
     * @param typeCode
     */
    private void fetchManufacturerItemsBuiltDatesFromServer(String manufacturerCode, String typeCode){
        // show loading
        showLoading.call();

        Disposable s = getDataManager().fetchManufacturerItemsBuiltDates(manufacturerCode,
                typeCode,
                AppConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    // hide loading dialog
                    hideLoading.call();

                    List<BuiltDate> list = mapToList(manufacturerCode, typeCode, response.getWkda());
                    // update manufacturers in db
                    insertManufacturers(list);

                    // notify subscribers about the new loaded data
                    builtDatesLiveData.setValue(list);

                }, throwable -> {
                    // hide loading dialog
                    hideLoading.call();

                    // notify subscribers about the error msg
                    getErrorMsg().setValue(throwable.getMessage());
                });
        getCompositeDisposable().add(s);
    }

    private List<BuiltDate> mapToList(String manufacturerCode, String typeCode, Map<String, String> map){
        List manuList = new ArrayList<BuiltDate>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String code = entry.getKey();
            String name = entry.getValue();
            manuList.add(new BuiltDate(manufacturerCode, typeCode, code, name));
        }
        return manuList;
    }

    /**
     * insert manufacturers in database
     *
     * @param builtDates
     */
    private void insertManufacturers(List<BuiltDate> builtDates){
        getCompositeDisposable().add(getDataManager().saveManufacturerItemsBuiltDates(builtDates)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    Log.d(TAG , builtDates.size()+" manufacturers inserted in db");
                } , throwable -> {
                    Log.d(TAG , "error inserting manufacturers : " +throwable.getMessage());
                }));
    }



    public MutableLiveData<List<BuiltDate>> getBuiltDatesLiveData() {
        if(builtDatesLiveData ==null)
            builtDatesLiveData = new MutableLiveData<List<BuiltDate>>();
        return builtDatesLiveData;
    }
}
