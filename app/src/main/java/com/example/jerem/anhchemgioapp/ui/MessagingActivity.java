package com.example.jerem.anhchemgioapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.adapter.MessengerAdapter;
import com.example.jerem.anhchemgioapp.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.OnClick;

public class MessagingActivity extends BaseAuthActivity {

    @BindView(R.id.listMessages)
    RecyclerView listMessages;
    private String convID;
    private DatabaseReference root, dbMessages;
    private FirebaseUser fUser;
    //    private FirebaseListAdapter<Message> arrayAdapter;
//    private ArrayList<Message> listMessage;
    private MessengerAdapter adapter;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.edtMessageBodyField)
    EditText edtMessageBodyField;

    private LinearLayoutManager linearLayoutManager;
//    @BindView(R.id.listMessages)
//    ListView lvMessage;

    @Override
    int getContentView() {
        return R.layout.activity_messaging;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Khai bao database
        root = FirebaseDatabase.getInstance().getReference();
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        if (intent == null) this.finish();
        convID = intent.getStringExtra("convID");

        Init();
    }

    @OnClick(R.id.btnSend)
    public void onClick() {
        Message message = MakeMessage(edtMessageBodyField.getText().toString(), fUser.getEmail());
        root.child("conversations").child(convID).child("messages").push().setValue(message);
        edtMessageBodyField.setText("");
        linearLayoutManager.setStackFromEnd(true);
    }


    private void Init() {
        listMessages.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        listMessages.setLayoutManager(linearLayoutManager);
        dbMessages = root.child("conversations").child(convID).child("messages");
        adapter = new MessengerAdapter(Message.class, MessengerAdapter.MessengerHolder.class, dbMessages, fUser.getEmail());
        listMessages.setAdapter(adapter);
    }

    private Message MakeMessage(String content, String userId) {
        Message message = new Message();
        message.setContent(content);
        message.setUserID(userId);
        //TODO: Add Time and UserId
        return message;
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {

    }

    @Override
    void onAuthError() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.messages_menu, menu);
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
                startActivity(new Intent(MessagingActivity.this, ProfileActivity.class));
                break;
            case R.id.action_logout:
                getFireBaseAuth().signOut();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
