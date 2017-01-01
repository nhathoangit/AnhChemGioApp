package com.example.jerem.anhchemgioapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jerem.anhchemgioapp.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by jerem on 07/12/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    abstract int getContentView();
    private Toolbar toolbar;
    private boolean isNeedRegister = false;

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

    protected void setActionBarIcon(int iconRes) {
        if(toolbar == null) return;
        toolbar.setNavigationIcon(iconRes);
    }

    protected void setDisplayHomeAsUpEnabled(boolean isHomeAsUp){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUp);
        }
    }

    protected void setNeedRegister(boolean isRegister){
        isNeedRegister = isRegister;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isNeedRegister){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isNeedRegister){
            EventBus.getDefault().unregister(this);
        }
    }

    public Toolbar getToolbar(){
        return toolbar;
    }
}
