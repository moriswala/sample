package com.company.mohammedyakub.data;

import com.company.mohammedyakub.data.local.db.DbHelper;
import com.company.mohammedyakub.data.remote.ApiService;
import com.company.mohammedyakub.data.local.prefs.PreferenceHelper;

public interface DataManager extends ApiService , PreferenceHelper , DbHelper{


}
