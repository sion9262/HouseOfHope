package com.example.houseofhope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.houseofhope.R;

import java.io.File;

public class PersonalColorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_personal_color);

        Button button1 = (Button) findViewById(R.id.PersonalStart) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(PersonalColorActivity.this, CaptureActivity.class);
                startActivity(registerPage);
            }
        });

        TextView txt = findViewById(R.id.personaltext);
        String tv = txt.getText().toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(tv);

        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ef0068")), 137, 175, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt.setText(ssb);

    }



}