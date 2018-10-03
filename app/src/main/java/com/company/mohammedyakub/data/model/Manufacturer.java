package com.company.mohammedyakub.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;

import android.support.annotation.NonNull;

@Entity(tableName = "manufacturer")
public class Manufacturer extends BaseObservable{

    @NonNull
    @PrimaryKey
    private String code;
    private String name;

    public Manufacturer(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
