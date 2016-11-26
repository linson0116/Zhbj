package com.example.linson.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.linson.zhbj.base.BasePager;

/**
 * Created by linson on 2016/11/26.
 */

public class HomePager extends BasePager {
    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tv_title.setText("首页");
        ib_menu.setVisibility(View.GONE);
        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        fl_base_pager_content.addView(textView);
    }
}
