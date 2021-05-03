package com.hp.appoindone;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class splash_screen extends AppCompatActivity {
    String pincode;
    TextView textView;
    int flag=0;
    String msg;
    int REQUEST_CODE = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = findViewById(R.id.tv_ss_notifier);
        if(!islocationenabled()){
            flag=1;
            Handler handler = new Handler();
            handler. postDelayed(new Runnable() {
                public void run() {
                     textView.setText("WE ARE TAKING YOU TO LOCATION SETTING PLEASE ENABLE IT");
                }
            }, 2000);
            Handler handler1 = new Handler();
            handler1. postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }, 3000);
        }
        else{
            Handler handler2 = new Handler();
            handler2. postDelayed(new Runnable() {
                public void run() {
                    islocationallowed();
                }
            }, 2000);
        }
    }

    private void islocationallowed(){
        if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            Log.i("heet", "moving to next without checking");
            serviceCheck();
        } else {
            Log.i("heet", "checking permision");
            ActivityCompat.requestPermissions(splash_screen.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("pin","onrequestpermissionresult function");
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("heetheet","onRequestPermissionResult");
                serviceCheck();
            }
        }
        if(requestCode == 45){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                nxtscreen();
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE},45);
            }
        }
    }

    private boolean islocationenabled(){
        Log.i("pin","islocationenabled function");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void nxtscreen(){
        Log.i("heetonstartservice","start");
        Intent intent = new Intent(this, LocationServices.class);
        intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
        startService(intent);
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent1 = new Intent(splash_screen.this,tutorial_screen.class);
            startActivity(intent1);
        }
        else
        {
            Intent intent1 = new Intent(splash_screen.this,MainActivity.class);
            startActivity(intent1);
        }
    }

    public void serviceCheck(){
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.FOREGROUND_SERVICE)==PackageManager.PERMISSION_GRANTED){
            nxtscreen();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE},45);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(flag==1){
            msg = "pauseForLocation";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(flag==1 && msg == ("pauseForLocation"))
        {
            if(islocationenabled())
            {
                textView.setText("");
                flag = 0;
                msg = "";
                islocationallowed();
            }
            else{
                Handler handler = new Handler();
                handler. postDelayed(new Runnable() {
                    public void run() {
                        textView.setText("PLEASE START LOCATION TO ENABLE VARIOUS FEATURES");
                    }
                }, 1000);
                Handler handler1 = new Handler();
                handler1. postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }, 3000);
            }
        }
    }
}