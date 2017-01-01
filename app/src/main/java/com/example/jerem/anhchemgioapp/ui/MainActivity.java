package com.example.jerem.anhchemgioapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.adapter.GroupAdapter;
import com.example.jerem.anhchemgioapp.model.Conversation;
import com.example.jerem.anhchemgioapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseAuthActivity{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private DatabaseReference root;
    private GroupAdapter adapter;
    private FirebaseUser user;


    @Override
    int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Khai bao database
        root = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(MainActivity.this);
            }
        });
    }

    public void showDialog(final Context ctx) {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Tạo mới cuộc trò chuyện");
        final EditText edtCreate = (EditText) dialog.findViewById(R.id.edtCreate);
        Button btnCreate = (Button) dialog.findViewById(R.id.btnCreate);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        dialog.show();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference fUser = root.child("users").child(user.getUid());
                final String titleName = edtCreate.getText().toString();
                final Conversation conversation = new Conversation();
                final Map<String, User> people = new HashMap<String, User>();
                conversation.setName(titleName);
                conversation.setPeoples(people);
                fUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User me = dataSnapshot.getValue(User.class);
                        conversation.getPeoples().put(user.getUid(), me);
                        String key = root.child("conversations").push().getKey();
                        conversation.setConversationID(key);
                        root.child("conversations").child(key).setValue(conversation);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {
        //Recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference groupRef = root.child("conversations");
        adapter = new GroupAdapter(Conversation.class, R.layout.group_chat_list, GroupAdapter.GroupHolder.class, groupRef);
        recyclerView.setAdapter(adapter);
    }


    @Override
    void onAuthError() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.action_logout:
                getFireBaseAuth().signOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
