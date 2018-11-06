package com.nodeveloper.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nodeveloper.myapplication.MainActivity;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.MyUser;
import com.nodeveloper.myapplication.utils.L;
import com.nodeveloper.myapplication.utils.ShareUtils;
import com.nodeveloper.myapplication.utils.StaticClass;
import com.nodeveloper.myapplication.utils.TextUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_uname;
    private EditText et_upass;
    private CheckBox keep_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ShareUtils.putBoolean(this, StaticClass.KEEP_PASS, keep_pass.isChecked());

        if (keep_pass.isChecked()) {
            ShareUtils.putString(this, StaticClass.USER_NAME, et_uname.getText().toString().trim());
            ShareUtils.putString(this, StaticClass.USER_PASS, et_upass.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, StaticClass.USER_NAME);
            ShareUtils.deleShare(this, StaticClass.USER_PASS);
        }
    }

    public void initView() {
        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);
        keep_pass = findViewById(R.id.keep_pass);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnReg = findViewById(R.id.btn_regs);
        TextView tvForgetpwd = findViewById(R.id.tv_forgetpwd);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        tvForgetpwd.setOnClickListener(this);

        //设置选中的状态
        boolean isChecked = ShareUtils.getBoolean(this, StaticClass.KEEP_PASS, false);
        keep_pass.setChecked(isChecked);

        if (isChecked) {
            //设置密码
            et_uname.setText(ShareUtils.getString(this, StaticClass.USER_NAME, ""));
            et_upass.setText(ShareUtils.getString(this, StaticClass.USER_PASS, ""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String name = et_uname.getText().toString().trim();
                String pass = et_upass.getText().toString().trim();

                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(pass)) {
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(pass);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                //判断邮箱
                                if (user.getEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "登陆失败" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
            case R.id.btn_regs:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_forgetpwd:
                break;
        }
    }
}
