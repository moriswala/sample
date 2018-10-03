package com.company.mohammedyakub.ui.Base;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mohammedyakub.utils.CommonUtils;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {
    // this can probably depend on showLoading/hideLoading SingleEvent of BaseViewModel.
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;
    private BaseActivity mActivity;
    private V mViewModel;

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        subscribeToDialogEvents();
        setHasOptionsMenu(false);
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(getActivity());
    }

    private void subscribeToDialogEvents() {
        mViewModel.getHideLoading().observe(mActivity, Void -> hideLoading());

        mViewModel.getShowLoading().observe(mActivity, Void -> showLoading());

        mViewModel.getErrorMsg().observe(mActivity, (Observer<String>) error -> {
            Log.d("error msg : ",error);
        });
    }
}