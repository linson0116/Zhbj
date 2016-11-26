package com.example.linson.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.linson.zhbj.R;

/**
 * Created by linson on 2016/11/26.
 */

public class BasePager {

    public Activity mActivity;
    public View rootView;
    public TextView tv_title;
    public ImageButton ib_menu;
    public FrameLayout fl_base_pager_content;

    public BasePager(Activity activity) {
        mActivity = activity;
        rootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_base_pager_content = (FrameLayout) view.findViewById(R.id.fl_base_pager_content);
        return view;
    }

    public void initData() {

    }
}
