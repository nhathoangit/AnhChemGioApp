package com.example.jerem.anhchemgioapp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.adapter.MessengerAdapter;
import com.example.jerem.anhchemgioapp.model.Conversation;
import com.example.jerem.anhchemgioapp.model.Message;
import com.example.jerem.anhchemgioapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MessagingActivity extends BaseAuthActivity {

    @BindView(R.id.listMessages)
    RecyclerView listMessages;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String convID;
    private String title;
    private DatabaseReference root, dbMessages;
    private FirebaseUser fUser;
    private boolean _flag = false;

    private ProgressDialog progressDialog;
    private MessengerAdapter adapter;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.edtMessageBodyField)
    EditText edtMessageBodyField;

    private LinearLayoutManager linearLayoutManager;

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
        title = intent.getStringExtra("title");

        toolbar.setTitle(title);
        Init();
        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
    }

    @OnClick(R.id.btnSend)
    public void onClick() {
        Message message = MakeMessage(edtMessageBodyField.getText().toString(), fUser.getEmail());
        root.child("conversations").child(convID).child("messages").push().setValue(message);
        root.child("conversations").child(convID).child("lastMessage").setValue(message.getContent());
        root.child("conversations").child(convID).child("time").setValue(new Date().getTime());
        edtMessageBodyField.setText("");
        linearLayoutManager.scrollToPosition(adapter.getItemCount());

    }


    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_member_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edtAdd = (EditText) dialogView.findViewById(R.id.edtAdd);
        final Map<String, User> people = new HashMap<String, User>();
	//thêm thành viên vào nhóm chat
        dialogBuilder.setTitle("Add member");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                _flag = true;
                root.child("conversations").child(convID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Conversation conversation = dataSnapshot.getValue(Conversation.class);

                        root.child("users").orderByChild("email").equalTo(edtAdd.getText().toString()).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (_flag == false) return;
                                progressDialog.setMessage("Đang tim kiem thanh vien " + edtAdd.getText().toString());
                                progressDialog.show();
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                                        conversation.getPeoples().put(user.getKey(), user.getValue(User.class));
                                        root.child("conversations").child(convID).setValue(conversation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(MessagingActivity.this, "Đã thêm " + edtAdd.getText().toString() + " vào nhóm!!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        _flag = false;
                                        //Toast.makeText(MessagingActivity.this, user.getKey(), Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(MessagingActivity.this, "Rất tiếc, " + edtAdd.getText().toString() + " không tồn tại!!!", Toast.LENGTH_SHORT).show();
                                }
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
				
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(MessagingActivity.this, "Đã có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MessagingActivity.this, "Đã có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                Toast.makeText(MessagingActivity.this, "Đã hủy", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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
        Message message = new Message(content, userId);
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
            case R.id.action_add_member:
                showDialog();
                break;
            case R.id.action_profile_members:
                Intent intent = new Intent(this, UsersActivity.class);
                intent.putExtra("convID", convID);
                startActivity(intent);
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
