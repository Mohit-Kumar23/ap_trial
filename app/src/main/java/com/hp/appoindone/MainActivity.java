package com.hp.appoindone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.login.LoginManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    LottieAnimationView toggle;
    FrameLayout frameLayout;
    NavigationView navigationView;
    CoordinatorLayout coordinatorLayout;
    String pincode;
    log_in lg_obj;
    splash_screen spls_obj;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lg_obj = new log_in();
        spls_obj = new splash_screen();
        Toast.makeText(this,FirebaseAuth.getInstance().getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
        initviews();
        checkInternet();
        pincode = spls_obj.pincode; //getIntent().getStringExtra("pincodepass");
        Log.i("pincode_mainactivity",String.valueOf(pincode));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottomNavigationView.getMenu().findItem(R.id.none).setEnabled(false);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                toggle.setFrame((int) (45 * slideOffset));
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        toggle.setOnClickListener(v -> {

            if (!drawerLayout.isDrawerVisible(GravityCompat.START)) {
                frameLayout.setTranslationZ(-6);
                coordinatorLayout.setTranslationZ(-5);
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
                frameLayout.setTranslationZ(0);
                coordinatorLayout.setTranslationZ(0);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new homeFragment(pincode)).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        temp = new homeFragment(pincode);
                        break;
                    case R.id.schedule:
                        temp = new scheduledFragment();
                        break;
                    case R.id.emergency:
                        temp = new emergencyFragment();
                        break;
                    case R.id.profile:
                        temp = new profileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, temp).commit();
                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.log_out: {
                                        if(lg_obj.signIn_flag!=1) {
                                            FirebaseAuth.getInstance().signOut();
                                        }
                                       else {
                                                FirebaseAuth.getInstance().signOut();
                                                lg_obj.mGoogleSignInClient.signOut();

                                            }
                                       startActivity(new Intent(MainActivity.this,tutorial_screen.class));
                                       finish();
                                       }
                                       return true;
                    default: return false;
                }

            }
        });
    }

    private void checkInternet() {
        boolean c = checkConnectivity();
        if(!c){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("No Internet Connection");
            alertDialogBuilder.setMessage("You Need to have Mobile Data or Wifi to access this.");
            alertDialogBuilder.show();
        }
    }

    private boolean checkConnectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void initviews(){
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = findViewById(R.id.hamburger);
        frameLayout = findViewById(R.id.frame_layout);
        coordinatorLayout = findViewById(R.id.bn_am_cl);
        navigationView = findViewById(R.id.navigation_view);
    }


}