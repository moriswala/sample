package com.company.mohammedyakub.data.remote.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ServerResponce {

    @SerializedName("page")
    int page;

    @SerializedName("pageSize")
    int pageSize;

    @SerializedName("totalPageCount")
    int totalPageCount;

    @SerializedName("wkda")
    HashMap<String, String> wkda;

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public HashMap<String, String> getWkda() {
        return wkda;
    }
}
