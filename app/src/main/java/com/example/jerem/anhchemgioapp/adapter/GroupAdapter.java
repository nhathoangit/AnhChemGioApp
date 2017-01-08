package com.example.jerem.anhchemgioapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.model.Conversation;
import com.example.jerem.anhchemgioapp.ui.MessagingActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        viewHolder.tvTime.setText(DateFormat.format("dd-MM-yyyy {HH:mm:ss}",model.getTime()));
        viewHolder.tvMessage.setText(model.getLastMessage());
//        if(model.getMessages().size() > 0){
//            Message latestMessage = model.getMessages().get(model.getMessages().size()-1);
//            viewHolder.tvMessage.setText( latestMessage.getContent());
//            viewHolder.tvTime.setText((CharSequence) ServerValue.TIMESTAMP);
//        }
    }

    public static class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvTitle;
        private final TextView tvMessage;
        private final TextView tvTime;
        private Context ctx;

        public GroupHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);

            ctx = itemView.getContext();
            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View view) {
            final ArrayList<Conversation> conversation = new ArrayList<>();
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("conversations");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        conversation.add(snapshot.getValue(Conversation.class));
                    }

                    Conversation con = conversation.get(getLayoutPosition());
//                    Toast.makeText(ctx, con.getConversationID(), Toast.LENGTH_SHORT).show();
                    int itemPosition = getLayoutPosition();
                    Intent intent = new Intent(ctx, MessagingActivity.class);
                    intent.putExtra("convID", con.getConversationID());
                    intent.putExtra("title",con.getName());
//                    intent.putExtra("position", itemPosition + "");
//                    intent.putExtra("restaurants", Parcels.wrap(restaurants));
                    ctx.startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}

