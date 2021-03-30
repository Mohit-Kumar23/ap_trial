package com.hp.appoindone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout Email,Password,First_name,Last_name,Phone_no,CPassword;
    TextInputEditText eEmail,ePassword,eFirst_name,eLast_name,ePhone_no,eCPassword;
    String email,password,first_name,last_name,phone_no,cPassword;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        initviews();

        button.setOnClickListener(view -> {
            convertviews();
            go();
        });
    }

   public void initviews()
    {
        eEmail = findViewById(R.id.tiet_s_email);
        eFirst_name = findViewById(R.id.tiet_s_fname);
        eLast_name = findViewById(R.id.tiet_s_lname);
        ePhone_no = findViewById(R.id.tiet_s_phone);
        ePassword = findViewById(R.id.tiet_s_pwd);
        eCPassword = findViewById(R.id.tiet_s_cpwd);
        First_name = findViewById(R.id.til_s_fname);
        Last_name = findViewById(R.id.til_s_lname);
        Phone_no = findViewById(R.id.til_s_phone);
        Email = findViewById(R.id.til_s_email);
        Password = findViewById(R.id.til_s_pwd);
        CPassword = findViewById(R.id.til_s_cpwd);
        button = findViewById(R.id.b_s_signup);

    }

    public void convertviews()
    {
        email = Email.getEditText().getText().toString().trim();
        password = Password.getEditText().getText().toString().trim();
        first_name = First_name.getEditText().getText().toString().trim();
        last_name = Last_name.getEditText().getText().toString().trim();
        phone_no = Phone_no.getEditText().getText().toString().trim();
        cPassword = CPassword.getEditText().getText().toString().trim();
    }

    public void go()
    {
        if(!validateEmail() | !validatePassword() | !validatename() | !validatephone() | !validatepwdsame()){
            return;
        }
        else {
            String Phone_No = "+91"+phone_no;
            Log.v("Phone",Phone_No);
            Intent intent = new Intent(sign_up.this,otp_screen.class);
            intent.putExtra("phone_no",Phone_No.trim());
            intent.putExtra("email",email);
            intent.putExtra("pwd",password);
            startActivity(intent);
        }
    }

    private boolean validateEmail()
    {
        if(email.isEmpty()) {
            eEmail.setError("Email can't be empty.");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("Invalid Email.");
            return false;
        }
        else {
            eEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword()
    {
        if(password.length()<8) {
            Password.setPasswordVisibilityToggleEnabled(false);
            ePassword.setError("Password must be 8 character long.");
            return false;
        }
        else if(!Utils.PASSWORD_PATTERN.matcher(password).matches()){
            Password.setPasswordVisibilityToggleEnabled(false);
            ePassword.setError("Minimum 1 lower & upper case, digit & special character required.");
            return false;
        }
        else{
            Password.setPasswordVisibilityToggleEnabled(true);
            ePassword.setError(null);
            return true;
        }
    }

    private boolean validatepwdsame()
    {
        if(!password.equals(cPassword)){
            CPassword.setPasswordVisibilityToggleEnabled(false);
            eCPassword.setError("Password not match.");
            return false;
        }
        else
        {
            CPassword.setPasswordVisibilityToggleEnabled(true);
            eCPassword.setError(null);
            return true;
        }
    }

    private boolean validatephone()
    {
        if(phone_no.length()<10 || phone_no.length()>13){
            ePhone_no.setError("Invalid Phone No.");
            return false;
        }
        else{
            ePhone_no.setError(null);
            return true;
        }
    }

    private boolean validatename()
    {
        if(first_name.isEmpty() || last_name.isEmpty()) {
            if (first_name.isEmpty()) {
                eFirst_name.setError("Name can't be empty.");
            }
            if (last_name.isEmpty()) {
                eLast_name.setError("Name can't be empty.");
            }
            return false;
        }
        else {
            eFirst_name.setError(null);
            eLast_name.setError(null);
            return true;
        }
    }


}