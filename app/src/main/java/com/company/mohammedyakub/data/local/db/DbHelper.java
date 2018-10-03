package com.company.mohammedyakub.data.local.db;

import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.model.ManufacturerItems;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface DbHelper {

    Observable<Boolean> saveManufacturers(List<Manufacturer> manufacturers);

    Observable<List<Manufacturer>> loadAllManufacturers();

    Observable<Boolean> saveManufacturerItems(List<ManufacturerItems> manufacturerItems);

    Observable<List<ManufacturerItems>> loadAllManufacturerItems();

}
