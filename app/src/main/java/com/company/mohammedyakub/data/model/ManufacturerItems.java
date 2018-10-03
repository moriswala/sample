package com.company.mohammedyakub.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

@Entity(tableName = "manufacturer_items")
public class ManufacturerItems extends BaseObservable{


    private String manufacturerCode;
    @NonNull
    @PrimaryKey
    private String code;
    private String name;

    public ManufacturerItems(String manufacturerCode, String code, String name) {
        this.manufacturerCode = manufacturerCode;
        this.code = code;
        this.name = name;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
