package com.example.jerem.anhchemgioapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.adapter.UsersAdapter;
import com.example.jerem.anhchemgioapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;

public class UsersActivity extends BaseAuthActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.usersRecyclerView)
    RecyclerView usersRecyclerView;
    private DatabaseReference root, listUsers;
    private FirebaseUser fUser;
    private String convID;
    private UsersAdapter adapter;

    @Override
    int getContentView() {
        return R.layout.activity_users;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Danh sách các thành viên");
        //Khai bao database
        root = FirebaseDatabase.getInstance().getReference();
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        if (intent == null) this.finish();
        convID = intent.getStringExtra("convID");

        Init();
    }

    private void Init() {
        usersRecyclerView.setHasFixedSize(true);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listUsers = root.child("conversations").child(convID).child("peoples");
        adapter = new UsersAdapter(User.class,R.layout.users_list, UsersAdapter.UsersHolder.class, listUsers);
        usersRecyclerView.setAdapter(adapter);
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {

    }

    @Override
    void onAuthError() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
