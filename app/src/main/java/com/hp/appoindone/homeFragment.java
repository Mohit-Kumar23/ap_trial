package com.hp.appoindone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class homeFragment extends Fragment{

    RecyclerView recView, recView2,recView3;
    mostrecent_adapter mrAdapter;
    nearby_adapter nbAdapter;
    category_adapter catAdapter;
    String pincode,email,emailName,gender;

    public homeFragment(String pincode) {
        this.pincode = pincode;
        Log.i("pincodeconstructor",String.valueOf(this.pincode));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailName = email.substring(0,email.indexOf('@'));
        Log.i("pinfb",String.valueOf(pincode));
        FirebaseRecyclerOptions<doctorclass> options =
                new FirebaseRecyclerOptions.Builder<doctorclass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor"), doctorclass.class)
                        .build();
        mrAdapter = new mostrecent_adapter(options);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        Query user = databaseReference.orderByChild("email").equalTo(email);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    pincode = snapshot.child(emailName).child("pincode").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        firebaseNearBy();
        categoryList();
    }


    private void firebaseNearBy() {
        FirebaseRecyclerOptions<doctorclass> options1 =
                new FirebaseRecyclerOptions.Builder<doctorclass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("doctor").orderByChild("pincode").startAt(pincode).endAt(pincode+"uf8ff"), doctorclass.class)
                        .build();
        nbAdapter = new nearby_adapter(options1);
    }

    public void categoryList()
    {
         FirebaseRecyclerOptions<categoryClass> options2 =
                new FirebaseRecyclerOptions.Builder<categoryClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("specialist"),categoryClass.class)
                        .build();
        catAdapter = new category_adapter(options2);
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
        recView3 = view.findViewById(R.id.recView3);
        recView3.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        recView3.setAdapter(catAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mrAdapter.startListening();
        nbAdapter.startListening();
        catAdapter.startListening();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        Query user = databaseReference.orderByChild("email").equalTo(email);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    gender = snapshot.child(emailName).child("gender").getValue(String.class);
                    if(gender==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Required Details");
                        builder.setMessage("Please fill the required Details");
                        builder.setIcon(R.mipmap.appoindone_logo);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((MainActivity)getContext()).onDialogClick();
                            }
                        });
                        builder.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        mrAdapter.stopListening();
        nbAdapter.stopListening();
        catAdapter.stopListening();
    }



}