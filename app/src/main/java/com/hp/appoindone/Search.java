package com.hp.appoindone;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity {
    FloatingActionButton fab;
    private View background;
    RecyclerView recyclerView;
    searchViewAdapter searchViewAdapter;
    TextInputEditText searchtext;
    RadioButton nameSearch,hnameSearch,addSearch,specialitySearch;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_search);
        fab = findViewById(R.id.fab_search);
        nameSearch = findViewById(R.id.nameSearch);
        hnameSearch = findViewById(R.id.hnameSearch);
        addSearch = findViewById(R.id.addSearch);
        specialitySearch = findViewById(R.id.specialitySearch);
        background = findViewById(R.id.rootLayout);
        fab.setOnClickListener(v -> onBackPressed());
        if (savedInstanceState == null) {
            background.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                });
            }

        }
        radioGroup = findViewById(R.id.radioGroupSearch);
        recyclerView = findViewById(R.id.recViewSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        searchtext = findViewById(R.id.tiet_search);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.nameSearch : searchProcessName(searchtext.getText());
                                break;
                            case R.id.hnameSearch : searchProcessHName(searchtext.getText());
                                break;
                            case R.id.addSearch : searchProcessAdd(searchtext.getText());
                                break;
                            case R.id.specialitySearch : searchProcessSpeciality(searchtext.getText());
                                break;
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void circularRevealActivity() {
        int cx = background.getWidth()/2;
        int cy = background.getBottom() - getDips(44);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(100);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    public void searchProcessName(CharSequence s){
        if(s==""){

        }
        else {
            FirebaseRecyclerOptions<doctorclass> options1 =
                    new FirebaseRecyclerOptions.Builder<doctorclass>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("name").startAt(String.valueOf(s)).endAt(String.valueOf(s)+"\uf8ff"), doctorclass.class)
                            .build();
            searchViewAdapter = new searchViewAdapter(options1,this);
            searchViewAdapter.startListening();
            recyclerView.setAdapter(searchViewAdapter);
        }
    }
    public void searchProcessHName(CharSequence s){
        if(s==""){

        }
        else {
            FirebaseRecyclerOptions<doctorclass> options1 =
                    new FirebaseRecyclerOptions.Builder<doctorclass>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("hname").startAt(String.valueOf(s)).endAt(String.valueOf(s)+"\uf8ff"), doctorclass.class)
                            .build();
            searchViewAdapter = new searchViewAdapter(options1,this);
            searchViewAdapter.startListening();
            recyclerView.setAdapter(searchViewAdapter);
        }
    }
    public void searchProcessSpeciality(CharSequence s){
        if(s==""){

        }
        else {
            FirebaseRecyclerOptions<doctorclass> options1 =
                    new FirebaseRecyclerOptions.Builder<doctorclass>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("specialist").startAt(String.valueOf(s)).endAt(String.valueOf(s)+"\uf8ff"), doctorclass.class)
                            .build();
            searchViewAdapter = new searchViewAdapter(options1,this);
            searchViewAdapter.startListening();
            recyclerView.setAdapter(searchViewAdapter);
        }
    }
    public void searchProcessAdd(CharSequence s){
        if(s==""){

        }
        else {
            FirebaseRecyclerOptions<doctorclass> options1 =
                    new FirebaseRecyclerOptions.Builder<doctorclass>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("address").startAt(String.valueOf(s)).endAt(String.valueOf(s)+"\uf8ff"), doctorclass.class)
                            .build();
            searchViewAdapter = new searchViewAdapter(options1,this);
            searchViewAdapter.startListening();
            recyclerView.setAdapter(searchViewAdapter);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getWidth()/2;
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(300);
            circularReveal.start();
        }
        else {
            super.onBackPressed();
        }
    }
    public void onClickCalled(String address, String contact_no, String hname, String mf, String name, String purl, String rating, String sat, String specialist, String sun, String mon_fri, String sat_sun, String fee) {
        Intent intent = new Intent(this, DoctorInfo.class);
        Bundle b = new Bundle();
        b.putString("address",address);
        b.putString("contact_no",contact_no);
        b.putString("hname",hname);
        b.putString("mf",mf);
        b.putString("name",name);
        b.putString("purl",purl);
        b.putString("rating",rating);
        b.putString("sat",sat);
        b.putString("specialist",specialist);
        b.putString("sun",sun);
        b.putString("mon_fri",mon_fri);
        b.putString("sat_sun",sat_sun);
        b.putString("fee",fee);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}