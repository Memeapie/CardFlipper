package com.sam.cardflipper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.sam.cardflipper.R;
import com.sam.cardflipper.Services.Impl.SoundEffectServiceImpl;
import com.sam.cardflipper.Services.SoundEffectService;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private final SoundEffectService soundEffectService = new SoundEffectServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        final ImageView image = findViewById(R.id.splashImage);

        AlphaAnimation animationOpen = new AlphaAnimation(0.0f, 1.0f);
        animationOpen.setDuration(1500);
        animationOpen.setStartOffset(0);
        animationOpen.setFillAfter(true);
        image.startAnimation(animationOpen);

        soundEffectService.playSoundEffect(this, R.raw.birdintro);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ActivityBar.class));
            }
        };

        Timer opening = new Timer();
        opening.schedule(task, 2500);
    }
}