package com.company.mohammedyakub.ui.manufacturerlist.paging;

import android.app.Application;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.remote.api.ServerResponce;
import com.company.mohammedyakub.di.ApplicationContext;
import com.company.mohammedyakub.utils.AppConstants;
import com.company.mohammedyakub.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManufacturerDataSource extends PageKeyedDataSource<Integer, Manufacturer> {

    public static final int PAGE_SIZE = 15;
    private static final int FIRST_PAGE = 0;
    private static final String TAG = ManufacturerDataSource.class.getSimpleName();
    private final DataManager dataManager;
    private final Application context;
    private final CompositeDisposable compositDisposable;
    private final int totalPageCount = 0;

    public ManufacturerDataSource(Application context, DataManager dataManager, CompositeDisposable compositDisposable) {
        this.context = context;
        this.dataManager = dataManager;
        this.compositDisposable = compositDisposable;

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Manufacturer> callback) {
        if(NetworkUtils.isNetworkConnected(context)) {
            loadInitialFromServer(callback);
        }else{
            loadInitialFromDb(callback);
        }
    }

    private void loadInitialFromDb(LoadInitialCallback<Integer, Manufacturer> callback) {
        compositDisposable.add(dataManager.loadAllManufacturers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(manufacturers->{
                    // notify subscribers about the loaded data
                    callback.onResult(manufacturers, null, null);
                } , throwable -> {
                    // notify subscribers about the error msg

                }));
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params,
                           @NonNull final LoadCallback<Integer, Manufacturer> callback) {
        if(NetworkUtils.isNetworkConnected(context)) {
            loadBeforeFromServer(params, callback);
        }
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Manufacturer> callback) {
        if(NetworkUtils.isNetworkConnected(context)) {
            loadAfterFromServer(params, callback);
        }
    }


    private void loadInitialFromServer(LoadInitialCallback<Integer, Manufacturer> callback) {
        dataManager.fetchManufacturersListCall(AppConstants.API_KEY, FIRST_PAGE, PAGE_SIZE)
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.body() != null) {
                            List<Manufacturer> list = mapToList(response.body().getWkda());
                            // update manufacturers in db
                            insertManufacturers(list);
                            callback.onResult(list, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponce> call, Throwable t) {

                    }
                });
    }



    private void loadBeforeFromServer(LoadParams<Integer> params, LoadCallback<Integer, Manufacturer> callback) {
        dataManager.fetchManufacturersListCall(AppConstants.API_KEY, params.key, PAGE_SIZE)
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.body() != null) {
                            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                            List<Manufacturer> list = mapToList(response.body().getWkda());
                            // update manufacturers in db
                            insertManufacturers(list);
                            callback.onResult(list, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponce> call, Throwable t) {

                    }
                });
    }



    private void loadAfterFromServer(LoadParams<Integer> params, LoadCallback<Integer, Manufacturer> callback) {
        dataManager.fetchManufacturersListCall(AppConstants.API_KEY, params.key, PAGE_SIZE)
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.body() != null && params.key < response.body().getTotalPageCount()) {
                            List<Manufacturer> list = mapToList(response.body().getWkda());
                            // update manufacturers in db
                            insertManufacturers(list);
                            callback.onResult(list, params.key + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponce> call, Throwable t) {

                    }
                });
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
        compositDisposable.add(dataManager.saveManufacturers(manufacturers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    Log.d(TAG , manufacturers.size()+" manufacturers inserted in db");
                } , throwable -> {
                    Log.d(TAG , "error inserting manufacturers : " +throwable.getMessage());
                }));
    }
}
