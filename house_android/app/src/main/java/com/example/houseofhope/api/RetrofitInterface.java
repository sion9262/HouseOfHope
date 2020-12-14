package com.example.houseofhope.api;

import com.example.houseofhope.api.Model.AuthModel;
import com.example.houseofhope.api.Model.GuestData;
import com.example.houseofhope.api.Model.Parking;
import com.example.houseofhope.api.Model.ResultCode;
import com.example.houseofhope.api.Model.UpdataParking;
import com.example.houseofhope.api.Model.UpdateGuest;
import com.example.houseofhope.api.Model.Upload;
import com.example.houseofhope.api.Model.UploadClass;
import com.example.houseofhope.src.Auth;
import com.example.houseofhope.src.Auth_guest;
import com.example.houseofhope.src.Auth_register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("login")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AuthModel> login(@Body Auth user);

    @POST("register")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AuthModel> register(@Body Auth_register user);

    @POST("regguest")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<GuestData> regguest(@Body Auth visitor);

    @POST("getuserguest")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<GuestData> getuserguest(@Body Auth_guest visitor);

    @POST("reguserface")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Upload> reguserface(@Body UploadClass data);

    @POST("updateguest")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Upload> updateguest(@Body UpdateGuest data);

    @GET("parking")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Parking> parking();

    @POST("updatecar")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Upload> updatecar(@Body UpdataParking data);


}
