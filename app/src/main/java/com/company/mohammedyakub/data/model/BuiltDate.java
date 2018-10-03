package com.company.mohammedyakub.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

@Entity(tableName = "built_date")
public class BuiltDate extends BaseObservable{


    private String manufacturerCode;
    private String typeCode;
    @NonNull
    @PrimaryKey
    private String code;
    private String name;

    public BuiltDate(String manufacturerCode, String typeCode, String code, String name) {
        this.manufacturerCode = manufacturerCode;
        this.typeCode = typeCode;
        this.code = code;
        this.name = name;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
