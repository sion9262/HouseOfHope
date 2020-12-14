package com.example.houseofhope;

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
import com.example.houseofhope.src.Auth_register;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout errEmail;
    private TextInputLayout errPassword;
    private TextInputLayout errPassword2;
    private TextInputLayout errPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }
    /*
        로그인 버튼 클릭하면 Auth 클래스 생성하여 로그인
     */
    public void register_button(View view){

        TextInputLayout emailLayout = findViewById(R.id.email2);
        TextInputLayout password2Layout = findViewById(R.id.password);
        TextInputLayout passwordLayout = findViewById(R.id.password2);
        TextInputLayout dongLayout = findViewById(R.id.dong);
        TextInputLayout hoLayout = findViewById(R.id.ho);
        TextInputLayout nameLayout = findViewById(R.id.name);
        TextInputLayout carLayout = findViewById(R.id.car);
        String email = emailLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        String password2 = password2Layout.getEditText().getText().toString();
        String name = nameLayout.getEditText().getText().toString();
        String dong = dongLayout.getEditText().getText().toString();
        String ho = hoLayout.getEditText().getText().toString();
        String car = carLayout.getEditText().getText().toString();

        Auth_register user = new Auth_register(email, password, name, dong, ho, car);
        try{
            register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void register(final Auth_register user) {
        /*
            로그인 로직
            이메일 검증
            패스워드 검증

         */
        errEmail = (TextInputLayout) findViewById(R.id.email2);
        errPassword2 = (TextInputLayout) findViewById(R.id.password);
        errPassword = (TextInputLayout) findViewById(R.id.password2);

        if (!user.CheckEmail()) {
            // editText 에 오류났다고 표시하는 부분
            errEmail.setErrorEnabled(true);
            errEmail.setError("올바른 이메일을 입력해주세요.");
        } else {
            errEmail.setErrorEnabled(false);
        }

        if (!user.CheckPassword()) {
            errPassword.setErrorEnabled(true);
            errPassword.setError("비밀번호가 너무 짧습니다.");
        } else {
            errPassword.setErrorEnabled(false);
        }
        /*
        if (!user.CheckPassword2()) {
            errPassword2.setErrorEnabled(true);
            errPassword2.setError("비밀번호가 일치하지 않습니다 .");
        } else {
            errPassword2.setErrorEnabled(false);
        }
        */

        // http 통신 시작
        if (user.CheckEmail() && user.CheckPassword()){
            RetrofitSender.getServer().register(user).enqueue(new Callback<AuthModel>() {
                @Override
                public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                    if (response.isSuccessful()) {
                        AuthModel result = response.body();
                        System.out.println(result.getResponseCode());
                        if (result.getResponseCode() == 200) {

                            // 로그인 성공시 prefs에 데이터를 넣어줌

                            Intent MainPage = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(MainPage);
                            finish();
                        }
                    } else {
                        System.out.println("실패");
                    }

                }

                @Override
                public void onFailure(Call<AuthModel> call, Throwable t) {
                    System.out.println("서버 꺼짐");
                }
            });
        }
    }

}