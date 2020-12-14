package com.example.houseofhope;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.houseofhope.api.Model.AuthModel;
import com.example.houseofhope.api.Model.Parking;
import com.example.houseofhope.api.Model.ParkingData;
import com.example.houseofhope.api.Model.UpdataParking;
import com.example.houseofhope.api.Model.Upload;
import com.example.houseofhope.api.RetrofitSender;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingActivity extends AppCompatActivity {

    TextView place1;
    TextView place2;
    TextView mycarnumber;
    TextView useparking;
    TextView mycar;
    Context mContext;
    int is_car = 2;
    int place1_car = 0;
    int place2_car = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        place1 = (TextView)findViewById(R.id.place1);
        place2 = (TextView)findViewById(R.id.place2);
        mycarnumber = (TextView)findViewById(R.id.mycarnumber);
        useparking = (TextView)findViewById(R.id.useparking);
        mycar = (TextView)findViewById(R.id.mycar);
        mContext = this;
        init();

        place1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place1_car == 0) show(1);
            }
        });
        place2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place2_car == 0) show(2);
            }
        });
    }
    public void show(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("자리 등록");
        builder.setMessage("자리 등록을 하시겠습니까?");
        builder.setPositiveButton("예",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        put_mycar(position);

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    public void put_mycar(final int position){
        SharedPreferences pref = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        String user_car = pref.getString("user_car", "");
        UpdataParking datas = new UpdataParking(user_car, position);
        RetrofitSender.getServer().updatecar(datas).enqueue(new Callback<Upload>() {
            @Override
            public void onResponse(Call<Upload> call, Response<Upload> response) {
                if(response.isSuccessful()){
                    Upload result = response.body();
                    if (result.getResponseCode() == 200){
                        if (position == 1){
                            place1.setBackgroundColor(mContext.getResources().getColor(R.color.mycar));
                            mycar.setText("A1");
                        }else {
                            place2.setBackgroundColor(mContext.getResources().getColor(R.color.mycar));
                            mycar.setText("B2");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Upload> call, Throwable t) {

            }
        });
    }



    public void init(){
        SharedPreferences pref = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        final String user_car = pref.getString("user_car", "");

        mycarnumber.setText(user_car);
        RetrofitSender.getServer().parking().enqueue(new Callback<Parking>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                if(response.isSuccessful()){
                    Parking result = response.body();
                    if (result.getResponseCode() == 200){
                        List<ParkingData> datas = result.getDatas();

                        if (datas.get(0).getStatus() == 1){
                            place1.setBackgroundColor(mContext.getResources().getColor(R.color.iscar));
                            place1_car = 1;
                            if (datas.get(0).getUser_id().equals(user_car)){
                                place1.setBackgroundColor(mContext.getResources().getColor(R.color.mycar));
                                mycar.setText("A1");
                            }
                            if (datas.get(0).getUser_id().equals("")){
                                place1_car = 0;
                            }

                            is_car--;
                        }else{
                            place1.setBackgroundColor(mContext.getResources().getColor(R.color.nocar));
                        }
                        if (datas.get(1).getStatus() == 1){

                            place2.setBackgroundColor(mContext.getResources().getColor(R.color.iscar));
                            place2_car = 1;
                            if (datas.get(1).getUser_id().equals(user_car)){
                                place2.setBackgroundColor(mContext.getResources().getColor(R.color.mycar));
                                mycar.setText("B2");
                            }
                            if (datas.get(1).getUser_id().equals("")){
                                place2_car = 0;
                            }
                            is_car--;
                        }else{
                            place2.setBackgroundColor(mContext.getResources().getColor(R.color.nocar));
                        }
                        useparking.setText(Integer.toString(is_car));
                    }
                }
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {

            }
        });


    }



}