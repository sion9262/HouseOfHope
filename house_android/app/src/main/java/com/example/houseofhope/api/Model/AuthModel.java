package com.example.houseofhope.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthModel {
    @Expose
    @SerializedName("responseCode")
    private Integer responseCode;

    @Expose
    @SerializedName("user_id")
    private Integer user_id;

    @Expose
    @SerializedName("user_name")
    private String user_name;

    @Expose
    @SerializedName("user_car")
    private String user_car;

    @Expose
    @SerializedName("user_dong")
    private String user_dong;

    @Expose
    @SerializedName("user_ho")
    private String user_ho;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("guest_name")
    private String guest_name;

    @Expose
    @SerializedName("guest_car")
    private String guest_car;

    @Expose
    @SerializedName("visit_dong")
    private String visit_dong;

    @Expose
    @SerializedName("visit_ho")
    private String visit_ho;

    @Expose
    @SerializedName("visit_why")
    private String visit_why;

    @Expose
    @SerializedName("visit_day")
    private String visit_day;

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("count_picture")
    private String count_picture;

    @Expose
    @SerializedName("published_at")
    private String published_at;

    @Expose
    @SerializedName("created_at")
    private String created_at;

    @Expose
    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("setUserInfo")
    @Expose
    private boolean setUserInfo;

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

    //regguest

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_car() {
        return user_car;
    }

    public void setUser_car(String user_car) {
        this.user_car = user_car;
    }

    public String getUser_dong() {
        return user_dong;
    }

    public void setUser_dong(String user_dong) {
        this.user_dong = user_dong;
    }

    public String getUser_ho() {
        return user_ho;
    }

    public void setUser_ho(String user_ho) {
        this.user_ho = user_ho;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_car() {
        return guest_car;
    }

    public void setGuest_car(String guest_car) {
        this.guest_car = guest_car;
    }

    public String getVisit_dong() {
        return visit_dong;

    }

    public boolean isSetUserInfo() {
        return setUserInfo;
    }

    public void setVisit_dong(String visit_dong) {
        this.visit_dong = visit_dong;
    }

    public String getVisit_ho() {
        return visit_ho;
    }

    public void setVisit_ho(String visit_ho) {
        this.visit_ho = visit_ho;
    }

    public String getVisit_why() {
        return visit_why;
    }

    public void setVisit_why(String visit_why) {
        this.visit_why = visit_why;
    }

    public String getVisit_day() {
        return visit_day;
    }

    public void setVisit_day(String visit_day) {
        this.visit_day = visit_day;
    }

    //getuserguest


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCount_picture() {
        return count_picture;
    }

    public void setCount_picture(String count_picture) {
        this.count_picture = count_picture;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }




}
