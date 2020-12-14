package com.example.houseofhope.src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Auth_register {
    private String user_email;
    private String user_password;
    private String user_password2;
    private String user_username;
    private String user_dong;
    private String user_ho;
    private String user_car;

    public Auth_register(String user_email, String user_password, String user_username, String user_dong, String user_ho, String user_car) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_username = user_username;
        this.user_dong = user_dong;
        this.user_ho = user_ho;
        this.user_car = user_car;
    }

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
