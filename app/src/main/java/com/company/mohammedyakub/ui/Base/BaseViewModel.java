package com.company.mohammedyakub.ui.Base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.utils.NetworkUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<V> extends AndroidViewModel {

    private final DataManager mDataManager;

    public final SingleLiveEvent<Void> showLoading = new SingleLiveEvent<>();

    public final SingleLiveEvent<Void> hideLoading = new SingleLiveEvent<>();

    public final MutableLiveData<String> errorMsg = new SingleLiveEvent<>();

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public BaseViewModel(Application context , DataManager dataManager) {
        super(context);
        this.mDataManager = dataManager;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SingleLiveEvent<Void> getShowLoading() {
        return showLoading;
    }

    public SingleLiveEvent<Void> getHideLoading() {
        return hideLoading;
    }

    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }

    public boolean isNetworkConnected(){
        return NetworkUtils.isNetworkConnected(getApplication());
    }
}
