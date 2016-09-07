package com.example.ahmad_elbayadi.review;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ahmad_Elbayadi on 06/09/2016.
 */
public class AppIntro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_intro);

        //4 second before openning the main activity
        int INTRO_TIME = 4000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(AppIntro.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.accelerate_interpolator);
                finish();
            }
        }, INTRO_TIME);
    }
}
