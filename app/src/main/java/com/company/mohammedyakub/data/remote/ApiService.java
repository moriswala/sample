package com.company.mohammedyakub.data.remote;

import com.company.mohammedyakub.data.remote.api.ServerResponce;
import com.company.mohammedyakub.utils.AppConstants;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v1/car-types/manufacturer")
    Observable<ServerResponce> fetchManufacturersList(
              @Query(AppConstants.Api.KEY_API)String api_key,
              @Query(AppConstants.Api.PAGE)Integer page,
              @Query(AppConstants.Api.PAGE_SIZE)Integer pageSize);

    @GET("v1/car-types/manufacturer")
    Call<ServerResponce> fetchManufacturersListCall(
            @Query(AppConstants.Api.KEY_API)String api_key,
            @Query(AppConstants.Api.PAGE)Integer page,
            @Query(AppConstants.Api.PAGE_SIZE)Integer pageSize);

    @GET("v1/car-types/main-types")
    Observable<ServerResponce> fetchManufacturerItemListOfManufacturerCode(
            @Query(AppConstants.Api.MANUFECTURERE)String manufacturer,
            @Query(AppConstants.Api.KEY_API)String api_key,
            @Query(AppConstants.Api.PAGE)Integer page,
            @Query(AppConstants.Api.PAGE_SIZE)Integer pageSize);

    @GET("v1/car-types/built-dates")
    Observable<ServerResponce> fetchManufacturerItemsBuiltDates(
            @Query(AppConstants.Api.MANUFECTURERE)String manufacturerCode,
            @Query(AppConstants.Api.MAIN_TYPE)String typeCode,
            @Query(AppConstants.Api.KEY_API)String apiKey);
}
