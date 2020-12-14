package com.example.houseofhope.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GuestData {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("data")
    @Expose
    private List<GuestModel> data;
    private GuestModel datas;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<GuestModel> getData() {
        return data;
    }

    public GuestModel getDatas() {return  datas;}

    public void setData(List<GuestModel> data) {
        this.data = data;
    }

    public void setDatas(GuestModel datas) {this.datas = datas;}
}
