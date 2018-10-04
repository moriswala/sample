package com.company.mohammedyakub.ui.manufacturerlist.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.remote.api.ServerResponce;
import com.company.mohammedyakub.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManufacturerDataSource extends PageKeyedDataSource<Integer, Manufacturer> {

    public static final int PAGE_SIZE = 15;
    private static final int FIRST_PAGE = 0;
    private final DataManager dataManager;

    public ManufacturerDataSource(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Manufacturer> callback) {
        dataManager.fetchManufacturersListCall(AppConstants.API_KEY, FIRST_PAGE, PAGE_SIZE)
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.body() != null) {
                            callback.onResult(mapToList(response.body().getWkda()), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponce> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Manufacturer> callback) {
        dataManager.fetchManufacturersListCall(AppConstants.API_KEY, params.key, PAGE_SIZE)
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.body() != null) {
                            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(mapToList(response.body().getWkda()), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponce> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Manufacturer> callback) {
        dataManager.fetchManufacturersListCall(AppConstants.API_KEY, params.key, PAGE_SIZE)
                .enqueue(new Callback<ServerResponce>() {
                    @Override
                    public void onResponse(Call<ServerResponce> call, Response<ServerResponce> response) {
                        if (response.body() != null && params.key < response.body().getTotalPageCount()) {
                            callback.onResult(mapToList(response.body().getWkda()), params.key + 1);
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
}
