package com.example.jerem.anhchemgioapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.model.Message;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;

public class MessagingActivity extends BaseAuthActivity {

    private String convID;
    private DatabaseReference root, dbMessages;
    private FirebaseListAdapter<Message> arrayAdapter;
    private ArrayList<Message> listMessage;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.edtMessageBodyField)
    EditText edtMessageBodyField;
    @BindView(R.id.listMessages)
    ListView lvMessage;

    @Override
    int getContentView() {
        return R.layout.activity_messaging;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        if (intent == null) this.finish();
        convID = intent.getStringExtra("convID");
        Init();
        //Toast.makeText(this, convID, Toast.LENGTH_SHORT).show();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = MakeMessage(edtMessageBodyField.getText().toString(), "ThuanNguyen");
                root.child("conversations").child(convID).child("messages").push().setValue(message);
                edtMessageBodyField.setText("");
            }
        });
    }


    private void Init() {
        root = FirebaseDatabase.getInstance().getReference();
        dbMessages = root.child("conversations").child(convID).child("messages");
        listMessage = new ArrayList<Message>();
        //arrayAdapter = new ArrayAdapter<Message>(this,android.R.layout.simple_list_item_1,listMessage);
        arrayAdapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.message_left, dbMessages) {
            @Override
            protected void populateView(View v, Message model, int position) {
                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(model.getUserID()=="ThuanNguyen")
                    v = inflater.inflate(R.layout.message_right,null);
                ((TextView)v.findViewById(R.id.txtMessage)).setText(model.getContent());
            }
        };
        lvMessage.setAdapter(arrayAdapter);
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
