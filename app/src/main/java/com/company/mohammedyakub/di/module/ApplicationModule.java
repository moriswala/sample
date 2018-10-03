package com.company.mohammedyakub.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.company.mohammedyakub.BuildConfig;
import com.company.mohammedyakub.data.AppDataManager;
import com.company.mohammedyakub.data.DataManager;
import com.company.mohammedyakub.data.local.db.AppDatabase;
import com.company.mohammedyakub.data.local.db.AppDbHelper;
import com.company.mohammedyakub.data.local.db.DbHelper;
import com.company.mohammedyakub.data.local.prefs.PreferenceHelper;
import com.company.mohammedyakub.data.local.prefs.SharedPreferenceUtils;
import com.company.mohammedyakub.di.ApiInfo;
import com.company.mohammedyakub.di.ApplicationContext;
import com.company.mohammedyakub.di.DatabaseInfo;
import com.company.mohammedyakub.di.PreferenceInfo;
import com.company.mohammedyakub.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module(includes = ApiServiceModule.class)
public class ApplicationModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Application context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).build();
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(SharedPreferenceUtils appPreferencesHelper) {
        return appPreferencesHelper;
    }


    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }


    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }
}
