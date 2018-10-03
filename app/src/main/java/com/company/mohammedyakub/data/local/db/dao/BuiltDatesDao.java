package com.company.mohammedyakub.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.company.mohammedyakub.data.model.BuiltDate;
import com.company.mohammedyakub.data.model.ManufacturerItems;

import java.util.List;

@Dao
public interface BuiltDatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BuiltDate> builtDates);

    @Query("SELECT * FROM built_date WHERE manufacturerCode =:manufacturerCode AND typeCode =:typeCode")
    List<BuiltDate> loadAllHavingManufacturerCodeAndTypeCode(String manufacturerCode, String typeCode);
}
