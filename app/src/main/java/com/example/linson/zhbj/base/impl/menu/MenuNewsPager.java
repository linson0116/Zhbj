package com.example.linson.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.linson.zhbj.R;
import com.example.linson.zhbj.base.BaseMenuPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by Administrator on 2016/12/1.
 */

public class MenuNewsPager extends BaseMenuPager {
    @ViewInject(R.id.tpi_menu_newspager)
    TabPageIndicator tpi_menu_newspager;
    @ViewInject(R.id.vp_menu_newspager)
    ViewPager vp_menu_newspager;
    public MenuNewsPager(Activity activity) {
        super(activity);
        ViewUtils.inject(mActivity);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(mActivity);
        textView.setText("菜单--新闻");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
