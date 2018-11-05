package com.nodeveloper.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nodeveloper.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager;

    private List<View> mList = new ArrayList<>();

    private View view1, view2, view3;

    private ImageView point1, point2, point3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }


    public void initView() {
        viewPager = findViewById(R.id.mViewPager);
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);

        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);

        //设置默认选中
        setPointImg(true, false, false);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        viewPager.setAdapter(new GuideAdapter());

        //监听滑动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        setPointImg(true, false, false);
                        break;
                    case 1:
                        setPointImg(false, true, false);
                        break;
                    case 2:
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
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager) container).addView(mList.get(position));
        }
    }
}
