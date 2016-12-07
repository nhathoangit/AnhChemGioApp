package com.example.jerem.anhchemgioapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by jerem on 07/12/2016.
 */

public abstract class BaseAuthActivity extends BaseActivity implements FirebaseAuth.AuthStateListener{

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            onAuthentication(firebaseAuth);
        }else{
            onAuthError();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    public FirebaseAuth getFireBaseAuth() {
        return mAuth;
    }

    abstract void onAuthentication(FirebaseAuth firebaseAuth);
    abstract void onAuthError();
}
