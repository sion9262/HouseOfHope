package com.example.houseofhope.src;


import android.os.AsyncTask;

import com.example.houseofhope.api.Model.AuthModel;
import com.example.houseofhope.api.RetrofitSender;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class Auth {
    private String user_email;
    private String user_password;
    private String user_password2;
    private String user_username;
    private String user_dong;
    private String user_ho;
    private String user_car;
    private String guest_name;
    private String guest_car;
    private String visit_dong;
    private String visit_ho;
    private String visit_why;
    private String visit_day;


    public Auth(String user_email, String user_password) {
        this.user_email = user_email;
        this.user_password = user_password;
    }

    public Auth(String guest_name, String guest_car, String visit_dong, String visit_ho, String visit_why, String visit_day) {
        this.guest_name = guest_name;
        this.guest_car = guest_car;
        this.visit_dong = visit_dong;
        this.visit_ho = visit_ho;
        this.visit_why = visit_why;
        this.visit_day = visit_day;
    }
    /*
    public Auth(String user_dong, String user_ho, String visit_why) {
        this.user_dong = user_dong;
        this.user_ho = user_ho;
    }

     */

    public boolean CheckEmail() {
        try {
            String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(user_email);
            if (m.matches()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean CheckPassword() {
        try {
            if (this.user_password.length() >= 6) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean CheckPassword2() {
        try {
            if (this.user_password.equals(this.user_password2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
