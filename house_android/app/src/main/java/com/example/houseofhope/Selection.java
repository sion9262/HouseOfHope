package com.example.houseofhope;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Selection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_selection);


        Button button1 = (Button) findViewById(R.id.Resident) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(Selection.this, AuthCheckPage.class);
                startActivity(registerPage);
            }
        });

        Button button2 = (Button) findViewById(R.id.Visitant) ;
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(Selection.this, VisitantActivity.class);
                startActivity(registerPage);
            }
        });

    }



}
