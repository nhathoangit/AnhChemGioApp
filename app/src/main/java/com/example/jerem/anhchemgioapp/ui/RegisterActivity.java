package com.example.jerem.anhchemgioapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerem.anhchemgioapp.R;
import com.example.jerem.anhchemgioapp.utils.MiscUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseAuthActivity {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnSignup)
    AppCompatButton btnSignup;
    @BindView(R.id.link_login)
    TextView linkLogin;

    @Override
    int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {

    }

    @Override
    void onAuthError() {

    }

    @OnClick({R.id.btnSignup, R.id.link_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignup:
                if (TextUtils.isEmpty(edtEmail.getText())) {
                    Toast.makeText(this, "Vui lòng nhập username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText())) {
                    Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                    return;
                }
                MiscUtils.showProcessDialog(this, "Đang đăng ký", "Hệ thống đang xử lý đăng ký, vui lòng chờ", true);
                getFireBaseAuth().createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    FirebaseAuthException ex = (FirebaseAuthException) task.getException();
                                    switch (ex.getErrorCode()) {
                                        case "ERROR_INVALID_EMAIL":
                                            Toast.makeText(RegisterActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            Toast.makeText(RegisterActivity.this, "Email đã bị sử dụng bởi người khác", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(RegisterActivity.this, "Đã có lỗi xảy ra, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                }
                                MiscUtils.cancleProcessDialog();
                            }
                        });
                break;
            case R.id.link_login:
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
                break;
        }
    }
}
