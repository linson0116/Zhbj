package com.example.linson.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.linson.zhbj.R;
import com.example.linson.zhbj.base.BaseMenuPager;
import com.example.linson.zhbj.bean.NewsBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

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

    public MenuNewsPager(Activity activity, NewsBean.MenuBean menuBean) {
        super(activity);
        mChildren = menuBean.children;
    }

    @Override
    public View initView() {
        Log.i(TAG, "initView: " + "初始化页面");
        View view = View.inflate(mActivity, R.layout.news_tab_detail_pager, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        Log.i(TAG, "initData: " + "初始化数据");
        MyPagerAdapter mAdapter = new MyPagerAdapter();
        vp_menu_newspager.setAdapter(mAdapter);
        tpi_menu_newspager.setViewPager(vp_menu_newspager);

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


            TextView textView = new TextView(mActivity);
            textView.setText("AAA1");
            textView.setTextColor(Color.RED);
            container.addView(textView);
            return textView;
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
