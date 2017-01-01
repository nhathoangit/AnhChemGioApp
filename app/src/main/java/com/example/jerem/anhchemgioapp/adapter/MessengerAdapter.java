package com.example.jerem.anhchemgioapp.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.model.Message;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

/**
 * Created by nhatt on 31/12/2016.
 */

public class MessengerAdapter extends FirebaseListAdapter<Message> {

    private String mUsername;

    public MessengerAdapter(Activity activity, Class<Message> modelClass, int modelLayout, Query ref, String mUsername) {
        super(activity, modelClass, modelLayout, ref);
    }
    //TODO 31/12/2016
    @Override
    protected void populateView(View v, Message model, int position) {
        ((TextView)v.findViewById(R.id.txtMessage)).setText(model.getContent());
    }

}
