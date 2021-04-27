package com.hp.appoindone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class splash_screen extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    String pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Handler handler = new Handler();
        handler. postDelayed(new Runnable() {
            public void run() {
                if(islocationenabled()){
                    getlocation();
                }
            }
        }, 5000);
    }

    @SuppressLint("MissingPermission")
    private void getlocation() {
        Log.i("pin","getlocation");
        if (checkpermission()){
            Log.i("pin","ifcheckpermission");
            if(islocationenabled()){
                Log.i("pin","ifislocationenabled");
                fusedlocationlistener();
            }
            else {
                Log.i("pin","elseislocationenabled");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            Log.i("pin","elsecheckpermission");
            requestPermissions();
            if(islocationenabled()){
                Log.i("pin","ifislocationenabled");
                fusedlocationlistener();
            }
            else {
                Log.i("pin","elseislocationenabled");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }

    private boolean checkpermission(){
        Log.i("pin","checkpermission function");
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        Log.i("pin","requestpermission function");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},44);

    }

    private boolean islocationenabled(){
        Log.i("pin","islocationenabled function");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    public void fusedlocationlistener(){
        Log.i("pin","fusedlocationlistener function");
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Log.i("pin","oncomplete");
                Location location = task.getResult();
                if(location == null){
                    Log.i("pin","location = null");
                }
                else {
                    Log.i("pin","location ! null");
                    Log.i("pincode",String.valueOf(location.getLatitude()));
                    Geocoder geocoder = new Geocoder(splash_screen.this, Locale.ENGLISH);
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                        pincode = addresses.get(0).getPostalCode();
                        Log.i("new pincode",String.valueOf(pincode));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                nxtscreen();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("pin","onrequestpermissionresult function");
        if(requestCode == 44){
            if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fusedlocationlistener();
                Log.i("pin","onRequestPermissionResult");
            }

        }
    }

    public void nxtscreen(){
        Intent intent = new Intent(splash_screen.this,tutorial_screen.class);
        intent.putExtra("pincodepass",pincode);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler. postDelayed(new Runnable() {
            public void run() {
                if(islocationenabled()){
                    getlocation();
                }
            }
        }, 5000);
    }
}