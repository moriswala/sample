package com.company.mohammedyakub.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.company.mohammedyakub.data.model.ManufacturerItems;

import java.util.List;

@Dao
public interface ManufacturerItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ManufacturerItems> manufacturerItems);

    @Query("SELECT * FROM manufacturer_items WHERE manufacturerCode =:code")
    List<ManufacturerItems> loadAllWithManufacturerCode(String code);
}
