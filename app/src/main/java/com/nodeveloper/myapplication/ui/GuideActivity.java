package com.nodeveloper.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.nodeveloper.myapplication.MainActivity;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.utils.L;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private List<View> mList = new ArrayList<>();

    private ImageView point1, point2, point3;

    private Button btn_start;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }


    public void initView() {
        ViewPager viewPager = findViewById(R.id.mViewPager);
        View view1 = View.inflate(this, R.layout.pager_item_one, null);
        View view2 = View.inflate(this, R.layout.pager_item_two, null);
        View view3 = View.inflate(this, R.layout.pager_item_three, null);

        btn_start = view3.findViewById(R.id.btn_start);
        iv_back = findViewById(R.id.iv_back);

        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);

        //设置默认选中
        setPointImg(true, false, false);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        btn_start.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        viewPager.setAdapter(new GuideAdapter());

        //监听滑动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                L.i(String.valueOf(i));
            }

            @Override
            public void onPageSelected(int i) {
                L.i(String.valueOf(i));
                switch (i) {
                    case 0:
                        iv_back.setVisibility(View.VISIBLE);
                        setPointImg(true, false, false);
                        break;
                    case 1:
                        iv_back.setVisibility(View.VISIBLE);
                        setPointImg(false, true, false);
                        break;
                    case 2:
                        iv_back.setVisibility(View.GONE);
                        setPointImg(false, false, true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheck3) {
        if (isCheck1) {
            point1.setBackgroundResource(R.drawable.icon_point_on);
        } else {
            point1.setBackgroundResource(R.drawable.icon_point_off);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.drawable.icon_point_on);
        } else {
            point2.setBackgroundResource(R.drawable.icon_point_off);
        }

        if (isCheck3) {
            point3.setBackgroundResource(R.drawable.icon_point_on);
        } else {
            point3.setBackgroundResource(R.drawable.icon_point_off);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
//                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;
        }

    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mList.get(position));
        }
    }
}
