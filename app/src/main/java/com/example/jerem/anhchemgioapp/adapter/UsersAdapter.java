package com.example.jerem.anhchemgioapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by jerem on 08/01/2017.
 */

public class UsersAdapter extends FirebaseRecyclerAdapter<User, UsersAdapter.UsersHolder> {

    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public UsersAdapter(Class<User> modelClass, int modelLayout, Class<UsersHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(UsersHolder viewHolder,User model, int position) {
        viewHolder.tvUserName.setText(model.getDisplayName());
    }

    public static class UsersHolder extends RecyclerView.ViewHolder {
        private final TextView tvUserName;

        public UsersHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
        }

        public void setUserName(String name) {
            tvUserName.setText(name);
        }

    }
}
