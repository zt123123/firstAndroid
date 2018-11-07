package com.nodeveloper.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nodeveloper.myapplication.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {
    private Button forget_btn;
    private Button update_btn;

    private EditText old_pass;
    private EditText new_pass;
    private EditText new_repass;
    private EditText forget_email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        update_btn = findViewById(R.id.btn_update_pass);
        forget_btn = findViewById(R.id.btn_update_forget);

        old_pass = findViewById(R.id.old_pass);
        new_pass = findViewById(R.id.new_pass);
        new_repass = findViewById(R.id.new_repass);
        forget_email = findViewById(R.id.et_forget_email);

        update_btn.setOnClickListener(this);
        forget_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_pass:
                String pass = old_pass.getText().toString().trim();
                String newpass = new_pass.getText().toString().trim();
                String repass = new_repass.getText().toString().trim();
                if (!TextUtils.isEmpty(pass) & !TextUtils.isEmpty(newpass) & !TextUtils.isEmpty(repass)) {
                    if (!newpass.equals(repass)) {
                        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    } else {
                        BmobUser.updateCurrentUserPassword(pass, newpass, new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPassActivity.this, "密码修改成功，可以用新密码进行登录啦", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgetPassActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(ForgetPassActivity.this, "密码修改失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "输入框不为空", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.btn_update_forget:
                final String email = forget_email.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    BmobUser.resetPasswordByEmail(email, new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPassActivity.this, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPassActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ForgetPassActivity.this, "重置密码失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }
    }
}
