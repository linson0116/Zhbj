package com.example.linson.zhbj.base.impl.menu;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.example.linson.zhbj.R;
import com.example.linson.zhbj.base.BaseMenuPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/12/1.
 */

public class MenuPicsPager extends BaseMenuPager {
    @ViewInject(R.id.lv_photos)
    ListView lv_photos;
    @ViewInject(R.id.gv_photos)
    GridView gv_photos;
    public MenuPicsPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.photos, null);
        ViewUtils.inject(mActivity, view);
        return view;
    }

    @Override
    public void initData() {

    }
}
