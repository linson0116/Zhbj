package com.example.linson.zhbj.base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.linson.zhbj.MainActivity;
import com.example.linson.zhbj.R;
import com.example.linson.zhbj.base.BaseMenuPager;
import com.example.linson.zhbj.base.NewsTabPagerDetail;
import com.example.linson.zhbj.bean.NewsBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/12/1.
 */

public class MenuNewsPager extends BaseMenuPager {
    private final List<NewsBean.TitleBean> mChildren;
    @ViewInject(R.id.tpi_menu_newspager)
    TabPageIndicator tpi_menu_newspager;
    @ViewInject(R.id.vp_menu_newspager)
    ViewPager vp_menu_newspager;
    private ArrayList<NewsTabPagerDetail> mNewsTabPagerList;

    public MenuNewsPager(Activity activity, NewsBean.MenuBean menuBean) {
        super(activity);
        mChildren = menuBean.children;
        Log.i(TAG, "MenuNewsPager: mChildren " + mChildren.size());
    }

    @Override
    public View initView() {
        Log.i(TAG, "initView: " + "初始化页面 MenuNewsPager");
        View view = View.inflate(mActivity, R.layout.menu_news_pager, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        Log.i(TAG, "initData: " + "初始化数据 MenuNewsPager");
        mNewsTabPagerList = new ArrayList<NewsTabPagerDetail>();
        for (int i = 0; i < mChildren.size(); i++) {
            mNewsTabPagerList.add(new NewsTabPagerDetail(mActivity, mChildren.get(i), i == 0));
        }
        MyPagerAdapter mAdapter = new MyPagerAdapter();
        vp_menu_newspager.setAdapter(mAdapter);
        tpi_menu_newspager.setViewPager(vp_menu_newspager);
        //
        tpi_menu_newspager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ((MainActivity) mActivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    ((MainActivity) mActivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mChildren.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsTabPagerDetail newsTabPagerDetail = mNewsTabPagerList.get(position);
            container.addView(newsTabPagerDetail.rootView);
            newsTabPagerDetail.initData();
            Log.i(TAG, "instantiateItem: position " + position);
            return newsTabPagerDetail.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mChildren.get(position).title;
        }
    }

}
