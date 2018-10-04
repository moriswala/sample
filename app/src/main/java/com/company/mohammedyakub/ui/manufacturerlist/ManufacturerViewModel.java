package com.company.mohammedyakub.ui.manufacturerlist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.ui.Base.BaseViewModel;
import com.company.mohammedyakub.ui.manufacturerlist.paging.ManufacturerDataSource;
import com.company.mohammedyakub.ui.manufacturerlist.paging.ManufacturerDataSourceFactory;

import javax.inject.Inject;

public class ManufacturerViewModel extends BaseViewModel {

    private static final String TAG = ManufacturerViewModel.class.getSimpleName();

    LiveData<PagedList<Manufacturer>> manufacturerLivePagedList;
    LiveData<PageKeyedDataSource<Integer, Manufacturer>> liveDataSource;

    @Inject
    public ManufacturerViewModel(Application context, DataManager dataManager) {
        super(context, dataManager);
        ManufacturerDataSourceFactory itemDataSourceFactory = new ManufacturerDataSourceFactory(dataManager);
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ManufacturerDataSource.PAGE_SIZE).build();

        manufacturerLivePagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }

//    /**
//     * fetch manufacturers from server OR local database based on network.
//     *
//     * if internet available then load manufacturers from server and update database
//     * else fetch manufacturers from database
//     */
//    void fetchManufacturerList(){
//        if(isNetworkConnected()){
//            fetchManufacturersFromServer();
//        }else{
//            fetchManufacturersFromDB();
//        }
//    }
//
//
//    /**
//     * fetch manufacturers from local database
//     *
//     */
//    private void fetchManufacturersFromDB(){
//        getCompositeDisposable().add(getDataManager().loadAllManufacturers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(manufacturers->{
//                    // notify subscribers about the loaded data
//                    manufacturerLivePagedList.setValue(manufacturers);
//                } , throwable -> {
//                    // notify subscribers about the error msg
//                    getErrorMsg().setValue(throwable.getMessage());
//                }));
//    }
//
//
//
//    /**
//     * fetch manufacturers from remote server
//     *
//     */
//    private void fetchManufacturersFromServer(){
//        // show loading
//        showLoading.call();
//
//        Disposable s = getDataManager().fetchManufacturersList(AppConstants.API_KEY, 0, 10)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    // hide loading dialog
//                    hideLoading.call();
//
//                    List<Manufacturer> list = mapToList(response.getWkda());
//                    // update manufacturers in db
//                    insertManufacturers(list);
//
//                    // notify subscribers about the new loaded data
//                    manufacturerLivePagedList.setValue(list);
//
//                }, throwable -> {
//                    // hide loading dialog
//                    hideLoading.call();
//
//                    // notify subscribers about the error msg
//                    getErrorMsg().setValue(throwable.getMessage());
//                });
//        getCompositeDisposable().add(s);
//    }
//
//    private List<Manufacturer> mapToList(Map<String, String> map){
//        List manuList = new ArrayList<Manufacturer>();
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            String code = entry.getKey();
//            String name = entry.getValue();
//            manuList.add(new Manufacturer(code, name));
//        }
//        return manuList;
//    }
//
//    /**
//     * insert manufacturers in database
//     *
//     * @param manufacturers
//     */
//    private void insertManufacturers(List<Manufacturer> manufacturers){
//        getCompositeDisposable().add(getDataManager().saveManufacturers(manufacturers)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response->{
//                    Log.d(TAG , manufacturers.size()+" manufacturers inserted in db");
//                } , throwable -> {
//                    Log.d(TAG , "error inserting manufacturers : " +throwable.getMessage());
//                }));
//    }



//    public LiveData<List<Manufacturer>> getManufacturerLiveData() {
//        if(manufacturerLivePagedList ==null) {
////            manufacturerLivePagedList = new MutableLiveData<List<Manufacturer>>();
//
//            ManufacturerDataSourceFactory itemDataSourceFactory = new ManufacturerDataSourceFactory();
//            liveDataSource = itemDataSourceFactory.getItemLiveDataSource();
//            PagedList.Config pagedListConfig =
//                    (new PagedList.Config.Builder())
//                            .setEnablePlaceholders(false)
//                            .setPageSize(ManufacturerDataSource.PAGE_SIZE).build();
//
//            manufacturerLivePagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
//                    .build();
//        }
//        return manufacturerLivePagedList;
//    }
}
