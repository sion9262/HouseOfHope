package com.example.imageupload.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSender {

    public static final String BASE_URL = "http://192.168.25.60:3000/";
    private static Retrofit retrofit = null;
    private static RetrofitInterface server = null;

    public static RetrofitInterface getServer(){
        if (server == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            server = retrofit.create(RetrofitInterface.class);
        }

        return server;
    }

}
