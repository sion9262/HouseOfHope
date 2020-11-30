package com.example.imageupload.api;

import com.example.imageupload.api.model.Auth;
import com.example.imageupload.api.model.BaseResponse;
import com.example.imageupload.api.model.Upload;
import com.example.imageupload.api.model.UploadClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("login")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<BaseResponse> login(@Body Auth user);

    @POST("reguserface")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Upload> reguserface(@Body UploadClass data);

}