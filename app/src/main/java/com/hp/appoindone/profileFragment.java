package com.hp.appoindone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profileFragment extends Fragment {

    FrameLayout frameLayout,edit;
    BottomNavigationView bottomNavigationView;
    String editTrigger;
    Button editbutton;
    MotionLayout motionLayout;
    public profileFragment(String editTrigger) {
        this.editTrigger = editTrigger;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        frameLayout = view.findViewById(R.id.profile_frame_layout);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView2);
        editFragment starteditFragment = new editFragment();
        getFragmentManager().beginTransaction().replace(R.id.edit_layout,starteditFragment).commit();
        editbutton = view.findViewById(R.id.button2);
        edit = view.findViewById(R.id.edit_layout);
        motionLayout = view.findViewById(R.id.profile_motionLayout);
        if(editTrigger=="open") {
            motionLayout.transitionToEnd();
        }

        getFragmentManager().beginTransaction().replace(R.id.profile_frame_layout,new personal_details_fragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()) {
                    case R.id.personal_details:
                        temp = new personal_details_fragment();
                        break;
                    case R.id.medical_details:
                        temp = new medical_details_fragment();
                        break;
                }
                getFragmentManager().beginTransaction().replace(R.id.profile_frame_layout, temp).commit();
                return true;
            }
        });
        return view;
    }

}