package com.company.mohammedyakub.data.local.db;

import com.company.mohammedyakub.data.model.BuiltDate;
import com.company.mohammedyakub.data.model.Manufacturer;
import com.company.mohammedyakub.data.model.ManufacturerItems;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {

    Observable<Boolean> saveManufacturers(List<Manufacturer> manufacturers);

    Observable<List<Manufacturer>> loadAllManufacturers();

    Observable<Boolean> saveManufacturerItems(List<ManufacturerItems> manufacturerItems);

    Observable<List<ManufacturerItems>> loadAllManufacturerItems(String code);

    Observable<Boolean> saveManufacturerItemsBuiltDates(List<BuiltDate> builtDates);

    Observable<List<BuiltDate>> loadAllManufacturerItemsBuiltDates(String manufacturerCode, String typeCode);

}
