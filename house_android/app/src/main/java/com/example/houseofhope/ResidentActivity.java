package com.example.houseofhope;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ResidentActivity extends AppCompatActivity {

    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        Button button1 = (Button) findViewById(R.id.Visitor_button) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(ResidentActivity.this, ListActivity.class);
                startActivity(registerPage);
            }
        });



        Button button3 = (Button) findViewById(R.id.Parking_button) ;
        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(ResidentActivity.this, ParkingActivity.class);
                startActivity(registerPage);
            }
        });



        Button button5 = (Button) findViewById(R.id.logout_button) ;
        button5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout(){
        /*
         * 로그아웃시 prefs 에 저장한 Auth를 지우고 로그인페이지로 이동
         *
         * */
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();


        Intent LoginPage = new Intent(ResidentActivity.this, AuthCheckPage.class);
        startActivity(LoginPage);
        finish();
    }

}