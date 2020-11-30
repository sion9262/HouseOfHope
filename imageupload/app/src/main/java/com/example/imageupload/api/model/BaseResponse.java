package com.example.imageupload.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class BaseResponse {
    @Expose
    @SerializedName("responseCode")
    private Integer responseCode;

    @Expose
    @SerializedName("user_id")
    private Integer user_id;


    @Expose
    @SerializedName("user_name")
    private String user_name;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
