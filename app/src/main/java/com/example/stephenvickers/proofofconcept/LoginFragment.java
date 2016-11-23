package com.example.stephenvickers.proofofconcept;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

/**
 * Created by stephenvickers on 11/17/16.
 */

public class LoginFragment extends Fragment {



    private EditText mEmail;

    private EditText mPassword;

    private Button mLoginButton;

    private Button mSignUpButton;



    public static final String TAG = "LoginFragment";

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);


        this.mEmail = (EditText) view.findViewById(R.id.login_email);
        this.mPassword = (EditText) view.findViewById(R.id.login_password);
        this.mLoginButton = (Button) view.findViewById(R.id.login_button);
        this.mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(mEmail.getText().toString(), mPassword.getText().toString());
                createNewFragment();
            }
        });

        this.mSignUpButton = (Button) view.findViewById(R.id.signup_button);
        this.mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmail.getText().toString(), mPassword.getText().toString());
                createNewFragment();
            }
        });

        this.mAuth = FirebaseAuth.getInstance();

        this.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                }
                else{
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }


            }
        };

        return view;
    }

    private void signIn(String email, String password){
        Log.d(TAG, "Sign In " + email);

        if(!validateForm()){
            return;
        }

        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail: onComplete" + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.w(TAG, "signInWithEmial:Failed", task.getException());
                    //Toast.makeText(LoginFragment.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void createAccount(String email, String password){
        Log.d(TAG, "Create Account" + email);
        if(!validateForm()){
            return;
        }

        this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete" + task.isSuccessful());

                if (!task.isSuccessful()){
                    //Toast.makeText(LoginFragment.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm(){
        boolean validForm = true;


        String email = this.mEmail.getText().toString();
        if(TextUtils.isEmpty(email)){
            this.mEmail.setError("Required");
            validForm = false;
        }
        else{
            this.mEmail.setError(null);
        }

        String password = this.mPassword.getText().toString();

        if(TextUtils.isEmpty(password)){
            this.mPassword.setError("Required");
            validForm = false;
        }
        else{
            this.mPassword.setError(null);
        }

        return validForm;
    }

    private Fragment createNewFragment(){
        return new ScoreFragment();
    }




}
