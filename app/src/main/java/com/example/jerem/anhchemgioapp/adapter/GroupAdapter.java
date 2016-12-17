package com.example.jerem.anhchemgioapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.model.Conversation;
import com.example.jerem.anhchemgioapp.model.Message;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by jerem on 15/12/2016.
 */

public class GroupAdapter extends FirebaseRecyclerAdapter<Conversation,GroupAdapter.GroupHolder>{

    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public GroupAdapter(Class<Conversation> modelClass, int modelLayout, Class<GroupHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(GroupHolder viewHolder, Conversation model, int position) {
        viewHolder.tvTitle.setText(model.getName());
        if(model.getMessages().size() > 0){
            Message latestMessage = model.getMessages().get(model.getMessages().size()-1);
            viewHolder.tvMessage.setText( latestMessage.getContent());
            viewHolder.tvTime.setText(latestMessage.getTime());
        }
    }

    public static class GroupHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvMessage;
        private final TextView tvTime;

        public GroupHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }

        public void setGroupTitle(String title) {
            tvTitle.setText(title);
        }

        public void setGroupMessage(String message) {
            tvMessage.setText(message);
        }

        public void setGroupTime(int time) {
            tvTime.setText(time);
        }

    }
}

