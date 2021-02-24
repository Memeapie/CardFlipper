package com.sam.cardflipper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sam.cardflipper.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ActivityBar.class));
            }
        };

        Timer opening = new Timer();
        opening.schedule(task, 2000);
    }
}