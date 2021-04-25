package com.hp.appoindone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profileFragment extends Fragment {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public profileFragment() {

    }

    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        frameLayout = view.findViewById(R.id.profile_frame_layout);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView2);
        getFragmentManager().beginTransaction().replace(R.id.profile_frame_layout,new general_details_fragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()) {
                    case R.id.general_details:
                        temp = new general_details_fragment();
                        break;
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