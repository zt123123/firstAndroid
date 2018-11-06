package com.nodeveloper.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.utils.ShareUtils;
import com.nodeveloper.myapplication.utils.StaticClass;
import com.nodeveloper.myapplication.utils.UtilTools;

public class SplashActivity extends AppCompatActivity {
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断是否是第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }


    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*
          延时2000ms
          判断是否第一次运行
          自定义字体
          Activity全屏主题
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    public void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        TextView tv_splash = findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setFont(this, tv_splash);
    }

    private boolean isFirst() {
        Boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            //设置为已经启动过APP
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    //禁止返回
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
