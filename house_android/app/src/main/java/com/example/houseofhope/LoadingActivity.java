package com.example.houseofhope;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        logo = (ImageView) findViewById(R.id.logo);

        new Handler( ) . postDelayed (new   Runnable ( )   {
            @ Override
            public   void   run ( )   {
                Intent intent   =   new   Intent ( LoadingActivity.this , Selection.class ) ;
                startActivity ( intent ) ;
                finish ( ) ;
            }
        } , 2000 ) ;

        Animation myanim   =   AnimationUtils. loadAnimation ( this, R.anim.mysplashanimation ) ;
        logo.startAnimation( myanim );

    }


}
