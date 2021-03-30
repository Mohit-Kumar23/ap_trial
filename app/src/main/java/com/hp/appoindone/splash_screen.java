package com.hp.appoindone;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;


public class splash_screen extends AppCompatActivity {
    Timer time;
    ImageView logo;
    Animation loading_anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initialize();
        time = new Timer();
        logo = findViewById(R.id.logo);
        logo.startAnimation(loading_anim);
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen.this,tutorial_screen.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }



    private void initialize()
    {
        loading_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading_anim);
    }
}