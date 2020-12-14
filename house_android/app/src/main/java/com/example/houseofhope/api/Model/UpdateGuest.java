package com.example.houseofhope.api.Model;

public class UpdateGuest {
    private String guest_id;
    private Integer status;


    public UpdateGuest(String guest_id, Integer status){
        this.guest_id = guest_id;
        this.status = status;

    }
}
