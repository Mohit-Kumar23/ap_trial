package com.hp.appoindone;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
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
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class userinfo extends AppCompatActivity implements PaymentResultListener {
    RadioGroup radioGroup;
    RadioButton rbmale,rbfemale,rbother;
    Button payBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initviews();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                    @Override
                                                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                      switch (checkedId) {
                                                          case R.id.rb_ui_male:
                                                              rbmale.setTextColor(Color.parseColor("#303030"));
                                                              rbfemale.setTextColor(Color.parseColor("#50303030"));
                                                              rbother.setTextColor(Color.parseColor("#50303030"));
                                                              break;
                                                          case R.id.rb_ui_female:
                                                              rbmale.setTextColor(Color.parseColor("#50303030"));
                                                              rbfemale.setTextColor(Color.parseColor("#303030"));
                                                              rbother.setTextColor(Color.parseColor("#50303030"));
                                                              break;
                                                          case R.id.rb_ui_other:
                                                              rbmale.setTextColor(Color.parseColor("#50303030"));
                                                              rbfemale.setTextColor(Color.parseColor("#50303030"));
                                                              rbother.setTextColor(Color.parseColor("#303030"));
                                                              break;
                                                      }
                                                  }
                                              });

            //Initializing Amount
            String Amount = "100";

            //Convert and RoundOff
            int amount = Math.round(Float.parseFloat(Amount) * 100);

            payBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        radioGroup = findViewById(R.id.radiogroup);
        rbmale = findViewById(R.id.rb_ui_male);
        rbfemale = findViewById(R.id.rb_ui_female);
        rbother = findViewById(R.id.rb_ui_other);
        //Assigning variable
        payBt = findViewById(R.id.pb_ui_btn);
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
        intent.putExtra("id",s);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        //Display Toast
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
