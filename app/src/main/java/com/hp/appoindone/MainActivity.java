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
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thelumiereguy.neumorphicview.views.NeumorphicCardView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    LottieAnimationView toggle;
    FrameLayout frameLayout;
    NavigationView navigationView;
    ImageView patient_photo;
    TextView textView;
    CoordinatorLayout coordinatorLayout;
    String pincode ;
    log_in lg_obj;
    splash_screen spls_obj;
    String emailName,email,purl,first_name,last_name;

    @SuppressLint({"RestrictedApi", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();
        checkInternet();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailName = email.substring(0,email.indexOf('@'));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        Query user = databaseReference.orderByChild("email").equalTo(email);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    purl = snapshot.child(emailName).child("purl").getValue(String.class);
                    first_name = snapshot.child(emailName).child("first_name").getValue(String.class);
                    last_name = snapshot.child(emailName).child("last_name").getValue(String.class);
                    //textView.setText(first_name+" "+last_name);
                    /*if(purl==null){
                        patient_photo.setImageResource(R.drawable.profile_icon);
                      }
                    else{
                        Glide.with(this).load(purl).into(patient_photo);
                    }*/
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lg_obj = new log_in();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        patient_photo.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new profileFragment()).commit());
        bottomNavigationView.getMenu().findItem(R.id.none).setEnabled(false);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                toggle.setFrame((int) (45 * slideOffset));
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                frameLayout.setTranslationZ(-6);
                coordinatorLayout.setTranslationZ(-5);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                frameLayout.setTranslationZ(0);
                coordinatorLayout.setTranslationZ(0);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Search.class);
                startActivity(intent);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new homeFragment(pincode)).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
                    temp = new profileFragment(null);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, temp).commit();
            return true;
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
        fab = findViewById(R.id.fab);

        //patient_photo = findViewById(R.id.patient_photo);
    }

    public void onClickCalled(String address, String contact_no, String hname, String mf, String name, String purl, String rating, String sat, String specialist, String sun,String mon_fri,String sat_sun,String fee) {
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

    public void onDialogClick() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new profileFragment("open")).commit();
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }

    public void onCategoryClicked(String name) {
        Intent intent = new Intent(this,category.class);
        intent.putExtra("category",name);
        startActivity(intent);
        finish();
    }

    public void onClickSave() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new profileFragment(null)).commit();

    }
}