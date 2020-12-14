package com.example.houseofhope.src;


import android.os.AsyncTask;

import com.example.houseofhope.api.Model.AuthModel;
import com.example.houseofhope.api.RetrofitSender;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class Auth_guest {
    private String user_dong;
    private String user_ho;


    public Auth_guest(String user_dong, String user_ho) {
        this.user_dong = user_dong;
        this.user_ho = user_ho;
    }

}
