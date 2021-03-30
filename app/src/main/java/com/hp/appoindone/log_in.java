package com.hp.appoindone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class log_in extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputLayout Email,Password;
    String email,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        initviews();

        login.setOnClickListener(view -> {
            convertviews();
            loginUserAccount(email,password);
        });
    }

    public void initviews(){
        Email = findViewById(R.id.til_l_email);
        Password = findViewById(R.id.til_l_pwd);
        login = findViewById(R.id.b_l_login);
    }

    public void convertviews(){
        email = Email.getEditText().getText().toString().trim();
        password = Password.getEditText().getText().toString().trim();
    }

    public void loginUserAccount(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(log_in.this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(log_in.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}