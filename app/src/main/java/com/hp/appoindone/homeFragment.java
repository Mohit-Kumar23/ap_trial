package com.hp.appoindone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class homeFragment extends Fragment{

    RecyclerView recView, recView2;
    mostrecent_adapter mrAdapter;
    nearby_adapter nbAdapter;
    String pincode;
    FusedLocationProviderClient fusedLocationProviderClient;

    public homeFragment(String pincode) {
        this.pincode = pincode;
        Log.i("pincodeconstructor",String.valueOf(this.pincode));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("pinfb",String.valueOf(pincode));
        if (pincode==null){
            requestlocationpermission();
        }
        FirebaseRecyclerOptions<doctorclass> options =
                new FirebaseRecyclerOptions.Builder<doctorclass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor"), doctorclass.class)
                        .build();
        mrAdapter = new mostrecent_adapter(options);
        firebaseNearBy();
    }

    private void firebaseNearBy() {
        FirebaseRecyclerOptions<doctorclass> options1 =
                new FirebaseRecyclerOptions.Builder<doctorclass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("pincode").startAt(pincode).endAt(pincode+"\uf8ff"), doctorclass.class)
                        .build();
        nbAdapter = new nearby_adapter(options1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recView = view.findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        recView.setAdapter(mrAdapter);
        recView2 = view.findViewById(R.id.recView2);
        recView2.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        recView2.setAdapter(nbAdapter);
        return view;
    }

    private LocationCallback locationCallBack = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Log.i("pin","onlocationresult function");
            fusedlocationlistener();
        }
    };

    @SuppressLint("MissingPermission")
    private void requestlocationpermission(){
        Log.i("pin","requsetlocationpermission function");
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack, Looper.myLooper());
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
                    Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                        String pincodethis = addresses.get(0).getPostalCode();
                        Log.i("new pincode",String.valueOf(pincodethis));
                        if(pincodethis!=null){
                            pincode = pincodethis;
                            firebaseNearBy();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mrAdapter.startListening();
        nbAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mrAdapter.stopListening();
        nbAdapter.stopListening();
    }



}