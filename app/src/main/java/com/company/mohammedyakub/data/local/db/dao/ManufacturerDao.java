package com.company.mohammedyakub.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.company.mohammedyakub.data.model.Manufacturer;

import java.util.List;

@Dao
public interface ManufacturerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Manufacturer> movies);

    @Query("SELECT * FROM manufacturer")
    List<Manufacturer> loadAll();
}
