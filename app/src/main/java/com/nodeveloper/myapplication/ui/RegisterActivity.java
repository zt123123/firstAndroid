package com.nodeveloper.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.MyUser;
import com.nodeveloper.myapplication.utils.StaticClass;
import com.nodeveloper.myapplication.utils.TextUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_user;
    private EditText et_email;
    private EditText et_pwd;

    private EditText et_age;
    private EditText et_desc;
    private EditText et_repwd;

    private RadioGroup radioGroup;

    private Button btnReg;

    private boolean isGender = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    public void initView() {
        et_user = findViewById(R.id.et_user);
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);

        et_age = findViewById(R.id.et_age);
        et_desc = findViewById(R.id.et_desc);
        et_repwd = findViewById(R.id.et_repwd);

        btnReg = findViewById(R.id.btn_register);
        radioGroup = findViewById(R.id.rg_gender);

        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                //获取输入框值
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = et_pwd.getText().toString().trim();
                String repass = et_repwd.getText().toString().trim();
                String email = et_email.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(name) &
                        !TextUtils.isEmpty(pass) &
                        !TextUtils.isEmpty(email)) {

                    //判断密码一致
                    if (pass.equals(repass)) {
                        //判断性别
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.gender_male) {
                                    isGender = true;
                                } else if (checkedId == R.id.gender_female) {
                                    isGender = false;
                                }
                            }
                        });

                        if (TextUtils.isEmpty(desc)) {
                            desc = "这个人很懒，什么都没留下";
                        }

                        //实体类
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(pass);
                        user.setAge(TextUtils.isEmpty(age) ? 0 : Integer.parseInt(age));
                        user.setEmail(email);
                        user.setSex(isGender);
                        user.setDesc(desc);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "注册失败" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
