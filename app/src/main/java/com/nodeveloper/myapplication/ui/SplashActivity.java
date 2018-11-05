package com.nodeveloper.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nodeveloper.myapplication.MainActivity;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.utils.ShareUtils;
import com.nodeveloper.myapplication.utils.StaticClass;

public class SplashActivity extends AppCompatActivity {
    private TextView tv_splash;
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
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
            }
        }


    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /**
         * 延时2000ms
         * 判断是否第一次运行
         * 自定义字体
         * Activity全屏主题
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    public void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        tv_splash = findViewById(R.id.tv_splash);
    }

    private boolean isFirst() {
        Boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST);
            return true;
        } else {
            return false;
        }
    }

}
