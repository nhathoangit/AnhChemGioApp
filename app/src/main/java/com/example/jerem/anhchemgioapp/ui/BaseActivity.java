package com.example.jerem.anhchemgioapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jerem.anhchemgioapp.R;

import butterknife.ButterKnife;

/**
 * Created by jerem on 07/12/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    abstract int getContentView();
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        configureToolbar();
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    protected void setDisplayHomeAsUpEnabled(boolean isHomeAsUp){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUp);
        }
    }

    public Toolbar getToolbar(){
        return toolbar;
    }
}
