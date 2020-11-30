package com.example.imageupload.api.model;

public class Auth {
    private String user_email;
    private String user_password;

    public Auth(String user_email, String user_password){
        this.user_email = user_email;
        this.user_password = user_password;

    }
}
