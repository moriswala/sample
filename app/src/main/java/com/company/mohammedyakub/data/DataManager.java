package com.company.mohammedyakub.data;

import com.company.mohammedyakub.data.local.db.DbHelper;
import com.company.mohammedyakub.data.model.ManufacturerItems;
import com.company.mohammedyakub.data.remote.ApiService;
import com.company.mohammedyakub.data.local.prefs.PreferenceHelper;
import com.company.mohammedyakub.data.remote.api.ManufacturerListResponce;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DataManager extends ApiService , PreferenceHelper , DbHelper{


}
