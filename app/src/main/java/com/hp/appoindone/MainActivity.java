package com.hp.appoindone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    LottieAnimationView toggle;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;
    LottieAnimationView lottieAnimationView;
    ImageView bottomnavigationimage;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toggle.setOnClickListener(v -> {

            if(!drawerLayout.isDrawerVisible(GravityCompat.START)){
                hamburger_open();
                drawerLayout.openDrawer(GravityCompat.START);
            }
            else{
                hamburger_close();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        floatingActionButton.setOnClickListener(v -> {
            if(lottieAnimationView.getFrame()==0 || lottieAnimationView.getFrame()==90){
                lottieAnimationView.setMinAndMaxFrame(0,45);
                lottieAnimationView.playAnimation();
            }
            if(lottieAnimationView.getFrame()==45){
                lottieAnimationView.setMinAndMaxFrame(45,91);
                lottieAnimationView.playAnimation();
            }
        });
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.history        : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
                case R.id.favourites     : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
                case R.id.settings       : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
                case R.id.help           : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
                case R.id.privacy_policy : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
                case R.id.about_us       : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
                case R.id.contact_us     : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
                case R.id.log_out        : drawerLayout.closeDrawer(GravityCompat.START);
                                           break;
            }
            return true; });

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,Search.class);
            startActivity(intent);
        });
    }

    public void hamburger_open(){
        toggle.setSpeed(3);
        toggle.setMinAndMaxFrame(0,45);
        toggle.playAnimation();
    }

    public void hamburger_close(){
        toggle.setSpeed(3);
        toggle.setMinAndMaxFrame(45,89);
        toggle.playAnimation();
    }

    public void initviews(){
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = findViewById(R.id.hamburger);
        bottomAppBar = findViewById(R.id.bottonappbar);
        floatingActionButton = findViewById(R.id.floatingactionbutton);
        lottieAnimationView = findViewById(R.id.search);
        bottomnavigationimage = findViewById(R.id.bottomnavigationimage);
    }

}