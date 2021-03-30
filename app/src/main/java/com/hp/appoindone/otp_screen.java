package com.hp.appoindone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class otp_screen extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editText1,editText2,editText3,editText4,editText5,editText6;
    String phone_no,email,pwd,systemcode,txt;
    TextView textview,resend;
    Button button;
    Timer time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        mAuth = FirebaseAuth.getInstance();
        phone_no = getIntent().getStringExtra("phone_no");
        email = getIntent().getStringExtra("email");
        pwd = getIntent().getStringExtra("pwd");
        initviews();
        otpview();
        txt="Enter OTP code sent to your number \n"+ phone_no;
        textview.setText(txt);
        sendotp(phone_no);
        button.setOnClickListener(v -> clickbutton(v));
        resend.setLinkTextColor(getColor(R.color.primarydark));
        resend.setOnClickListener(v -> {
            sendotp(phone_no);
        });
    }

    private void initviews(){
        resend = findViewById(R.id.ltv_otp_resend);
        button = findViewById(R.id.b_otp_continue);
        textview = findViewById(R.id.tv_otp_no);
        editText1 = findViewById(R.id.et_otp_1);
        editText2 = findViewById(R.id.et_otp_2);
        editText3 = findViewById(R.id.et_otp_3);
        editText4 = findViewById(R.id.et_otp_4);
        editText5 = findViewById(R.id.et_otp_5);
        editText6 = findViewById(R.id.et_otp_6);
    }

    private void sendotp(String phone_no){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone_no)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callback)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            systemcode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String smscode = phoneAuthCredential.getSmsCode();
            if(!smscode.isEmpty()){
                verifycode();
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp_screen.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void verifycode() {
        createUserAccount(email,pwd);
    }

    private void createUserAccount(String email,String pwd){
        mAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        updateUI();
                    } else {
                        Toast.makeText(otp_screen.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickbutton(View view){
        String code = editText1.getEditableText().toString().trim() + editText2.getEditableText().toString().trim() + editText3.getEditableText().toString().trim() +
                editText4.getEditableText().toString().trim() + editText5.getEditableText().toString().trim() + editText6.getEditableText().toString().trim();
        if(!code.isEmpty()){
            verifycode();
        }
    }

    public void updateUI(){
        dialog dialog = new dialog(otp_screen.this);
        dialog.startloadingdialog();
        time = new Timer();
        Handler handler = new Handler();
        handler.postDelayed(() -> dialog.dismissdialog(),3000);
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(otp_screen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
        }

    public void otpview(){
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    editText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    editText3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    editText4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    editText5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    editText6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}