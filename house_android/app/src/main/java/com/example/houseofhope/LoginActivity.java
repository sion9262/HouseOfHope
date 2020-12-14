package com.example.houseofhope;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.houseofhope.api.Model.AuthModel;
import com.example.houseofhope.api.RetrofitSender;
import com.example.houseofhope.src.Auth;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout errEmail;
    private TextInputLayout errPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registerForm = (TextView)findViewById(R.id.registerText);
        registerForm.setText(Html.fromHtml("<font color=\"#eeac99\" ><u>회원가입</u></font>"));
        registerForm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }
        });
    }
    /*
        로그인 버튼 클릭하면 Auth 클래스 생성하여 로그인
     */
    public void login_button(View view){

        TextInputLayout emailLayout = findViewById(R.id.email);
        TextInputLayout passwordLayout = findViewById(R.id.password);
        String email = emailLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        /*SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id",email);
        editor.putString("pw",password);
        */
        Auth user = new Auth(email, password);
        try{
            login(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void login(Auth user){
        /*
            로그인 로직
            이메일 검증
            패스워드 검증

         */
        errEmail = (TextInputLayout) findViewById(R.id.email);
        errPassword = (TextInputLayout) findViewById(R.id.password);

        if ( !user.CheckEmail()){
            // editText 에 오류났다고 표시하는 부분
            errEmail.setErrorEnabled(true);
            errEmail.setError("올바른 이메일을 입력해주세요.");
        }else{
            errEmail.setErrorEnabled(false);
        }

        if ( !user.CheckPassword()) {
            errPassword.setErrorEnabled(true);
            errPassword.setError("비밀번호가 너무 짧습니다.");
        }else {errPassword.setErrorEnabled(false);}

        // http 통신 시작
        RetrofitSender.getServer().login(user).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.isSuccessful()){
                    AuthModel result = response.body();
                    System.out.println(result.getResponseCode());
                    if (result.getResponseCode() == 200) {
                        // 로그인 성공시 prefs에 데이터를 넣어줌
                        try {

                            SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("user_id",result.getUser_id());
                            editor.putString("user_name",result.getUser_name());
                            editor.putString("user_car",result.getUser_car());
                            editor.putString("user_dong",result.getUser_dong());
                            editor.putString("user_ho",result.getUser_ho());
                            //editor.putString("status",result.getStatus());
                            editor.commit();

                            /*SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
                            String id = pref.getString("id", "");
                            String pw = pref.getString("pw", "");
                            */

                            Intent MainPage = new Intent(LoginActivity.this, CaptureActivity.class);
                            MainPage.putExtra("is_user", 1);
                            startActivity(MainPage);
                            finish();

                               /* Intent MainPage = new Intent(LoginActivity.this, ResidentActivity.class);
                                startActivity(MainPage);
                                finish();*/


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else {
                        View contextView = findViewById(R.id.password);

                        Snackbar.make(contextView, "올바른 정보를 입력해주세요.", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }else{
                    System.out.println(response);
                }

            }
            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                System.out.println("서버 꺼짐");
            }
        });

    }



}