package com.example.linson.zhbj.base;

import android.app.Activity;
import android.view.View;

import com.example.linson.zhbj.R;
import com.lidroid.xutils.ViewUtils;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewsTabPagerDetail {
    public Activity mActivity;
    public View rootView;


    public NewsTabPagerDetail(Activity activity) {
        mActivity = activity;
        initView();
        initData();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.news_tab_detail_pager, null);
        rootView = view;
        ViewUtils.inject(this, view);
        return rootView;
    }

    public void initData() {
    }

}
