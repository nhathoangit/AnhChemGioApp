package com.example.jerem.anhchemgioapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends BaseAuthActivity {


    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnSignup)
    Button btnSignup;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @Override
    int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Xac nhan user da dang nhap
    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    void onAuthError() {

    }

    @OnClick({R.id.btnLogin, R.id.btnSignup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (TextUtils.isEmpty(edtEmail.getText())) {
                    Toast.makeText(this, "Vui lòng nhập username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText())) {
                    Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                    return;
                }
                MiscUtils.showProcessDialog(this, "Đang đăng nhập", "Hệ thống đang xử lý đăng nhập, vui lòng chờ", true);
                getFireBaseAuth().signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            FirebaseAuthException ex = ((FirebaseAuthException) task.getException());
                                            switch (ex.getErrorCode()) {
                                                case "ERROR_INVALID_EMAIL":
                                                    Toast.makeText(LoginActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                                                    break;
                                                case "ERROR_WRONG_PASSWORD":
                                                    Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                                    break;
                                                case "ERROR_USER_NOT_FOUND":
                                                    Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
                                                    break;
                                                default:
                                                    Toast.makeText(LoginActivity.this, "Đã có lỗi xảy ra, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        } else {
                                            Intent iMainAct = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(iMainAct);
                                        }
                                        MiscUtils.cancleProcessDialog();
                                    }
                                }
                        );
                break;
            case R.id.btnSignup:
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                break;
        }
    }
}
