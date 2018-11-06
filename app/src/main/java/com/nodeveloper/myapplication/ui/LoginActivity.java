package com.nodeveloper.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nodeveloper.myapplication.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }


    public void initView() {
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnReg = findViewById(R.id.btn_reg);
        TextView tvForgetpwd = findViewById(R.id.tv_forgetpwd);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        tvForgetpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_reg:
                startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_forgetpwd:
                break;
        }
    }
}
