package com.example.imageupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imageupload.api.RetrofitSender;
import com.example.imageupload.api.model.Auth;
import com.example.imageupload.api.model.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login_btn;
    Auth user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        login_btn = (Button)findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 공백 설절 알아서.
                // email.getText().toString().length() > 0 ...
                user = new Auth(email.getText().toString(), password.getText().toString());
                login(user);
            }

        });

    }

    private void login(Auth user) {

        RetrofitSender.getServer().login(user).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse result = response.body();
                    if (result.getResponseCode() == 200){
                        Log.d("성공", "로그인 성공");

                    }
                } else {
                    Log.e("에러", "회원정보 에러");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("에러", call.toString());
                Log.e("에러", t.getMessage());
            }

        });

    }

}