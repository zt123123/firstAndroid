package com.nodeveloper.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.MyUser;
import com.nodeveloper.myapplication.ui.LoginActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_logout;
    private Button confirm__update_btn;

    private TextView et_user_info;

    private EditText user_update_sex;
    private EditText user_update_age;
    private EditText user_update_desc;
    private EditText user_update_name;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_user, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        btn_logout = view.findViewById(R.id.user_logout);
        confirm__update_btn = view.findViewById(R.id.confirm__update_btn);

        user_update_sex = view.findViewById(R.id.user_update_sex);
        user_update_age = view.findViewById(R.id.user_update_age);
        user_update_desc = view.findViewById(R.id.user_update_desc);
        user_update_name = view.findViewById(R.id.user_update_name);

        et_user_info = view.findViewById(R.id.et_user_info);

        btn_logout.setOnClickListener(this);
        confirm__update_btn.setOnClickListener(this);
        et_user_info.setOnClickListener(this);

        //默认不可输入
        setEnable(false);

        //设置默认值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);

        user_update_sex.setText(userInfo.isSex() ? "男" : "女");
        user_update_age.setText(userInfo.getAge() + "");
        user_update_desc.setText(userInfo.getDesc());
        user_update_name.setText(userInfo.getUsername());
    }

    public void setEnable(boolean is) {
        user_update_sex.setEnabled(is);
        user_update_age.setEnabled(is);
        user_update_desc.setEnabled(is);
        user_update_name.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_logout:

                //退出登录  清除缓存
                MyUser.logOut();

                //user为null
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.et_user_info:
                setEnable(true);
                confirm__update_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.confirm__update_btn:
                String sex = user_update_sex.getText().toString().trim();
                String age = user_update_age.getText().toString().trim();
                String desc = user_update_desc.getText().toString().trim();
                String name = user_update_name.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(sex) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(desc)) {
                    MyUser newUser = new MyUser();
                    newUser.setUsername(name);
                    newUser.setAge(Integer.parseInt(age));
                    newUser.setDesc(!TextUtils.isEmpty(desc) ? desc : "这个人很懒，什么都没留下");
                    newUser.setSex(age.equals("男"));

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnable(false);
                                confirm__update_btn.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "更新用户信息成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "更新用户信息失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
