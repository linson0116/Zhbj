package com.example.linson.zhbj;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {

    private ViewPager vp_guide;
    private List<ImageView> mList;
    private LinearLayout ll_points;
    private int[] mPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initUI();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mList = new ArrayList<>();

        for (int i = 0; i < mPics.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mPics[i]);
            mList.add(imageView);
        }
        vp_guide.setAdapter(new MyPagerAdapter());
    }

    private void initUI() {
        mPics = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ll_points = (LinearLayout) findViewById(R.id.ll_points);
        for (int i = 0; i < mPics.length; i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
            if (i != 0) {
                params.leftMargin = 50;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.points_gray);
            ll_points.addView(view, i);
        }
        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
}
