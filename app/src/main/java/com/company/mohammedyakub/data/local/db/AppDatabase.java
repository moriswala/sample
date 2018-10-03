package com.company.mohammedyakub.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.company.mohammedyakub.data.local.db.dao.ManufacturerDao;
import com.company.mohammedyakub.data.local.db.dao.ManufacturerItemsDao;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.model.ManufacturerItems;

@Database(entities = {Manufacturer.class, ManufacturerItems.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ManufacturerDao manufacturerDao();
    public abstract ManufacturerItemsDao manufacturerItemDao();
}