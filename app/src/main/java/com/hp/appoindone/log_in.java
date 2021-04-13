package com.hp.appoindone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;



import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class log_in extends AppCompatActivity {

    private static final String TAG ="Log_in_Activity" ;
    FirebaseAuth mAuth;
    TextInputLayout Email,Password;
    String email,password;
    Button login;
    LoginButton loginButton;
    CallbackManager mCallbackManager;
    private static final String EMAIL="email";
    private static final String PUBLIC_PROFILE="public_profile";
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        initviews();
        
    FacebookSdk.sdkInitialize(getApplicationContext());



        login.setOnClickListener(view -> {
            convertviews();
            loginUserAccount(email,password);
            startActivity(new Intent(log_in.this,MainActivity.class));
        });

        callbackManager = CallbackManager.Factory.create();


        loginButton.setPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("Facebook Login","CancelCall");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("Facebook Login","ErrorCall"+exception);
            }
        });
    }


    public void initviews(){
        Email = findViewById(R.id.til_l_email);
        Password = findViewById(R.id.til_l_pwd);
        login = findViewById(R.id.b_l_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);

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

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser==null)
            Toast.makeText(this,"No User Logged In",Toast.LENGTH_LONG).show();
        else
            startActivity(new Intent(log_in.this,MainActivity.class));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(log_in.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}