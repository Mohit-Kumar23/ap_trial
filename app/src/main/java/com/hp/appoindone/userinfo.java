package com.hp.appoindone;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class userinfo extends AppCompatActivity implements PaymentResultListener {
    RadioGroup radioGroup;
    RadioButton rbmale,rbfemale,rbother;
    Button payBt;
    String email,emailName,first_name,last_name,add,area,city,phone_no,dob,pincode,gender;
    TextInputEditText namel,emaill,phonel,addressl,areal,cityl,pincodel,descl;
    EditText agel;
    Spinner bg_sp;
    int flag;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initviews();
        b = getIntent().getExtras();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailName = email.substring(0,email.indexOf('@'));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(emailName);
        Log.i("heetdr",String.valueOf(databaseReference));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    first_name = String.valueOf(snapshot.child("first_name").getValue());
                    last_name = String.valueOf(snapshot.child("last_name").getValue());
                    dob = String.valueOf(snapshot.child("dob").getValue());
                    add = String.valueOf(snapshot.child("add").getValue());
                    area = String.valueOf(snapshot.child("area").getValue());
                    city = String.valueOf(snapshot.child("city").getValue());
                    pincode = String.valueOf(snapshot.child("pincode").getValue());
                    gender = String.valueOf(snapshot.child("gender").getValue());
                    phone_no = String.valueOf(snapshot.child("phone_no").getValue());
                    email = String.valueOf(snapshot.child("email").getValue());
                    namel.setText(first_name+" "+last_name);
                    addressl.setText(add);
                    areal.setText(area);
                    cityl.setText(city);
                    pincodel.setText(pincode);
                    if(gender=="Male")
                        rbmale.setChecked(true);
                    else if(gender=="Female")
                        rbfemale.setChecked(true);
                    else
                        rbother.setChecked(true);
                    phonel.setText(phone_no);
                    emaill.setText(email);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                    @Override
                                                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                      switch (checkedId) {
                                                          case R.id.rb_ui_male:
                                                              rbmale.setTextColor(Color.parseColor("#303030"));
                                                              rbfemale.setTextColor(Color.parseColor("#50303030"));
                                                              rbother.setTextColor(Color.parseColor("#50303030"));
                                                              flag=0;
                                                              break;
                                                          case R.id.rb_ui_female:
                                                              rbmale.setTextColor(Color.parseColor("#50303030"));
                                                              rbfemale.setTextColor(Color.parseColor("#303030"));
                                                              rbother.setTextColor(Color.parseColor("#50303030"));
                                                              flag=1;
                                                              break;
                                                          case R.id.rb_ui_other:
                                                              rbmale.setTextColor(Color.parseColor("#50303030"));
                                                              rbfemale.setTextColor(Color.parseColor("#50303030"));
                                                              rbother.setTextColor(Color.parseColor("#303030"));
                                                              flag=2;
                                                              break;
                                                      }
                                                  }
                                              });

            //Initializing Amount
            String Amount = getIntent().getExtras().getString("fee");    //+++++++++++++

            //Convert and RoundOff
            int amount = Math.round(Float.parseFloat(Amount) * 100);

            payBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    b.putString("userName",namel.getText().toString());
                    b.putString("userEmail",emaill.getText().toString());
                    b.putString("userAge",agel.getText().toString());
                    switch (flag)
                    {
                        case 0 : b.putString("userGender","Male");
                                 break;

                        case 1 : b.putString("userGender","Female");
                                 break;

                        case 2 : b.putString("userGender","Other");
                                 break;
                    }
                    b.putString("userPhone",phonel.getText().toString());


                    //Initialization of Razorpay checkout
                    Checkout checkout = new Checkout();
                    //Setting KeyID
                    checkout.setKeyID("rzp_test_uDJfpKMKc4hoUw");
                    //Initialize JSON object
                    JSONObject object = new JSONObject();
                    try {
                        //Put name
                        object.put("name", "CodeBlack");
                        //Put description
                        object.put("Description", "Payment Testing");
                        //Theme color
                        object.put("theme.color","#0093DD");
                        //Unit of currency
                        object.put("Unit","INR");
                        //Put amount
                        object.put("amount", amount);
                        //Put mobile
                        object.put("prefill.contact","9876543210");
                        //Put mail
                        object.put("prefill.email","codeblack.mah@gmail.com");
                        //Razor pay checkout activity
                        checkout.open(userinfo.this,object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    private void initviews() {
        namel = (TextInputEditText)findViewById(R.id.tiet_ui_name);
        emaill = (TextInputEditText)findViewById(R.id.tiet_ui_email);
        phonel = (TextInputEditText)findViewById(R.id.tiet_ui_phoneno);
        agel = (EditText)findViewById(R.id.et_ui_age);
        areal = (TextInputEditText)findViewById(R.id.tiet_ui_area);
        cityl = (TextInputEditText)findViewById(R.id.tiet_ui_city);
        addressl = (TextInputEditText)findViewById(R.id.tiet_ui_add);
        pincodel = (TextInputEditText)findViewById(R.id.tiet_ui_pincode);
        radioGroup = findViewById(R.id.radiogroup);
        rbmale = findViewById(R.id.rb_ui_male);
        rbfemale = findViewById(R.id.rb_ui_female);
        rbother = findViewById(R.id.rb_ui_other);
        //Assigning variable
        payBt = findViewById(R.id.pb_ui_btn);
        bg_sp = (Spinner)findViewById(R.id.sp_ui_bg);
        descl = (TextInputEditText)findViewById(R.id.tiet_ui_desc);
    }

    @Override
    public void onPaymentSuccess(String s) {
        //Alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Wait");
        builder.setMessage("Your payment is under process");
        //Showing alert dialog
        builder.show();
        Intent intent = new Intent(this, confirmAppoint.class);
        b.putString("paymentId",s);
        b.putString("desc",descl.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        //Display Toast
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,DoctorInfo.class));
    }
}
