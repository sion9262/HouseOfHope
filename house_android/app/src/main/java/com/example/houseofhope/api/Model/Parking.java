package com.example.houseofhope.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Parking {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("datas")
    @Expose
    private List<ParkingData> datas;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<ParkingData> getDatas() {
        return datas;
    }
    public void setDatas(List<ParkingData> datas) {
        this.datas = datas;
    }

}
