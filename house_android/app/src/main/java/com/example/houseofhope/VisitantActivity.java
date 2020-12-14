package com.example.houseofhope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.houseofhope.api.Model.AuthModel;
import com.example.houseofhope.api.Model.GuestData;
import com.example.houseofhope.api.Model.GuestModel;
import com.example.houseofhope.api.RetrofitSender;
import com.example.houseofhope.src.Auth;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.houseofhope.api.Model.AuthModel;
import com.example.houseofhope.api.RetrofitSender;
import com.example.houseofhope.src.Auth;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_visit_application);


        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String date = year + "/" + (month + 1) + "/" + dayOfMonth;
                System.out.println(date);

                SharedPreferences prefs = getSharedPreferences("Date", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("date", date);
                editor.commit();
            }
        });
    }

    public void visit_button(View view){

        TextInputLayout visitorNameLayout = findViewById(R.id.visitor_name);
        TextInputLayout carLayout = findViewById(R.id.carNumber);
        TextInputLayout dongLayout = findViewById(R.id.visit_dong);
        TextInputLayout hoLayout = findViewById(R.id.visit_ho);
        TextInputLayout visitorPurposeLayout = findViewById(R.id.visitorPurpose);

        String visit_dong = dongLayout.getEditText().getText().toString();
        String visit_ho = hoLayout.getEditText().getText().toString();
        String guest_car = carLayout.getEditText().getText().toString();
        String guest_name = visitorNameLayout.getEditText().getText().toString();
        String visit_why = visitorPurposeLayout.getEditText().getText().toString();

        SharedPreferences pref = getSharedPreferences("Date", Activity.MODE_PRIVATE);
        String date = pref.getString("date", "");
        String visit_day = date;

        System.out.println(visit_day);

        SharedPreferences prefs = getSharedPreferences("VisitorInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_dong",visit_dong);
        editor.putString("user_ho",visit_ho);
        editor.commit();

        Auth visitor = new Auth(guest_name, guest_car, visit_dong, visit_ho, visit_why, visit_day);
        try{
            visitor(visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button button1 = (Button) findViewById(R.id.visit_button) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(VisitantActivity.this, CaptureActivity.class);
                startActivity(registerPage);
            }
        });
    }

    private void visitor(final Auth visitor) {

        // http 통신 시작
        RetrofitSender.getServer().regguest(visitor).enqueue(new Callback<GuestData>() {
            @Override
            public void onResponse(Call<GuestData> call, Response<GuestData> response) {
                if (response.isSuccessful()) {
                    GuestData result = response.body();
                    System.out.println(result.getResponseCode());
                    if (result.getResponseCode() == 200) {

                        // 로그인 성공시 prefs에 데이터를 넣어줌
                        /*
                        List<GuestModel> guest = result.getData();
                        Integer id = guest.get(0).getId();
                        */
                        GuestModel guest = result.getDatas();
                        Integer id = guest.getId();

                        SharedPreferences prefs = getSharedPreferences("GuestInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("guest_id", id);
                        editor.commit();

                        Intent MainPage = new Intent(VisitantActivity.this, CaptureActivity.class);
                        MainPage.putExtra("is_user", 2);
                        startActivity(MainPage);
                        finish();

                    }
                } else {
                    System.out.println("실패");
                }

            }

            @Override
            public void onFailure(Call<GuestData> call, Throwable t) {
                System.out.println("서버 꺼짐");
            }
        });

    }

}