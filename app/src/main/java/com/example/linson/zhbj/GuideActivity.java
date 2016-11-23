package com.example.linson.zhbj;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class GuideActivity extends Activity {

    private ViewPager vp_guide;
    private List<ImageView> mList;
    private LinearLayout ll_points;
    private int[] mPics;
    private View v_point_red;

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
        v_point_red = findViewById(R.id.v_point_red);
        for (int i = 0; i < mPics.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(R.drawable.points_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                params.leftMargin = 10;
            }
            view.setLayoutParams(params);
            ll_points.addView(view);
        }
        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, "onPageScrolled: position=" + position + " positionOffset=" + positionOffset + " positionOffsetPixels=" + positionOffsetPixels);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v_point_red.getLayoutParams();
                layoutParams.leftMargin = (int) (40 * (position + positionOffset));
                v_point_red.setLayoutParams(layoutParams);
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
