package com.example.jerem.anhchemgioapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.model.Message;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by nhatt on 31/12/2016.
 */

public class MessengerAdapter extends FirebaseRecyclerAdapter<Message,MessengerAdapter.MessengerHolder> {

    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */

    private static final int RIGHT_MSG = 0;
    private static final int LEFT_MSG = 1;
    private String userID;

    public MessengerAdapter(Class modelClass, Class viewHolderClass, Query ref, String userID) {
        super(modelClass, R.layout.message_left, viewHolderClass, ref);
        this.userID = userID;
    }

    @Override
    protected void populateViewHolder(MessengerHolder viewHolder, Message model, int position) {
        viewHolder.tvMessage.setText(model.getContent());
        viewHolder.tvSender.setText(model.getUserID());
        viewHolder.tvDate.setText(DateFormat.format("dd-MM-yyyy {HH:mm:ss}",model.getTime()));
        if(position != 0){
            Message topMessage = getItem(position-1);
            if(topMessage != null){
                if(topMessage.getUserID().equals(model.getUserID())){
                    viewHolder.tvSender.setVisibility(View.GONE);
                }else{
                    viewHolder.tvSender.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public MessengerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == RIGHT_MSG){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_right,parent,false);
            return new MessengerHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_left,parent,false);
            return new MessengerHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message model = getItem(position);
        if (model.getUserID().equals(userID)) {
            return RIGHT_MSG;
        } else {
            return LEFT_MSG;
        }
    }

    public static class MessengerHolder extends RecyclerView.ViewHolder {
        private final TextView tvSender;
        private final TextView tvDate;
        private final TextView tvMessage;

        public MessengerHolder(View itemView) {
            super(itemView);
            tvSender = (TextView) itemView.findViewById(R.id.tvSender);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }

        public void setGroupTitle(String name) {
            tvSender.setText(name);
        }

        public void setGroupMessage(String message) {
            tvMessage.setText(message);
        }

        public void setGroupTime(int time) {
            tvDate.setText(time);
        }

    }
}
