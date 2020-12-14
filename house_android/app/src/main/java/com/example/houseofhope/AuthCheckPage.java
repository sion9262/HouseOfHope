package com.example.houseofhope;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


/*
* 로딩 후 activity 정해주는 것.
*
* */
public class AuthCheckPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        int check = checkLogin();
        System.out.println(check);
        if(check == 1) {
            Intent LoginPage = new Intent(this, LoginActivity.class);
            startActivity(LoginPage);
            finish();
        } else if (check == 0) {
            Intent SetUpPage = new Intent(this, ResidentActivity.class);
            startActivity(SetUpPage);
            finish();
        }

    }

    private int checkLogin(){
        SharedPreferences pref = getSharedPreferences("UserInfo", MODE_PRIVATE);

        String check = pref.getString("user_name", "");


        /*
         * 로그인이 안되있다면 1
         * 로그인이 됬다면 초기 셋팅 안했다면 2
         * 그게 아니면 0
         * */
        if (check == "") {
            return 1;
        } else{
            return 0;
        }
    }


}

