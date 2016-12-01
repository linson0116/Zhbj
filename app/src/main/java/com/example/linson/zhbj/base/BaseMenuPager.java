package com.example.linson.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016/12/1.
 */

public abstract class BaseMenuPager {
    public Activity mActivity;
    public View rootView;

    public BaseMenuPager(Activity activity) {
        mActivity = activity;
        rootView = initView();
    }

    public abstract View initView();

    public void initData() {

    }
}
