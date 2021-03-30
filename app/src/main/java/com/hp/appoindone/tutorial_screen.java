package com.hp.appoindone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class tutorial_screen extends AppCompatActivity {
    private ViewPager slideviewpager;
    private LinearLayout dotslayout;
    private SliderAdapter sliderAdapter;
    private ImageView[] dots;
    Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_screen);
        initviews();
        sliderAdapter = new SliderAdapter(this);
        slideviewpager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        slideviewpager.addOnPageChangeListener(viewlistener);
        login.setOnClickListener(view -> {
            Intent intent = new Intent(tutorial_screen.this,log_in.class);
            startActivity(intent);
        });
        signup.setOnClickListener(view -> {
            Intent intent = new Intent(tutorial_screen.this,sign_up.class);
            startActivity(intent);
        });
    }

    public void initviews()
    {
        slideviewpager = findViewById(R.id.slideviewpager);
        dotslayout = findViewById(R.id.slider);
        login = findViewById(R.id.b_ts_login);
        signup = findViewById(R.id.b_ts_signup);
    }
    public void addDotsIndicator(int position){
        dots = new ImageView[4];
        dotslayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.dot);
            dots[i].setScaleX((float) 0.5);
            dots[i].setScaleY((float) 0.5);
            dotslayout.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[position].setBackground(getResources().getDrawable(R.drawable.button_gradient));
        }

    }
    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}