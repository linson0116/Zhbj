package com.example.linson.zhbj.base.impl;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.example.linson.zhbj.MainActivity;
import com.example.linson.zhbj.base.BaseMenuPager;
import com.example.linson.zhbj.base.BasePager;
import com.example.linson.zhbj.base.impl.menu.MenuInteractPager;
import com.example.linson.zhbj.base.impl.menu.MenuNewsPager;
import com.example.linson.zhbj.base.impl.menu.MenuPicsPager;
import com.example.linson.zhbj.base.impl.menu.MenuSubjectPager;
import com.example.linson.zhbj.bean.NewsBean;
import com.example.linson.zhbj.fragment.LeftMenuFragment;
import com.example.linson.zhbj.utils.ConstantsUtils;
import com.example.linson.zhbj.utils.SpUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by linson on 2016/11/26.
 */

public class NewsPager extends BasePager {

    public List<BaseMenuPager> mPagerList = new ArrayList<>();
    public NewsPager(Activity activity) {
        super(activity);
        mPagerList.add(new MenuNewsPager(activity));
        mPagerList.add(new MenuSubjectPager(activity));
        mPagerList.add(new MenuPicsPager(activity));
        mPagerList.add(new MenuInteractPager(activity));
    }

    @Override
    public void initData() {
        tv_title.setText("新闻");
        ib_menu.setVisibility(View.GONE);
//        TextView textView = new TextView(mActivity);
//        textView.setText("新闻");
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(20);
//        textView.setGravity(Gravity.CENTER);
        fl_base_pager_content.addView(new MenuNewsPager(mActivity).rootView);
        //取缓存数据
        String str = SpUtils.getString(mActivity, ConstantsUtils.NEWS_URL);
        if (str != null) {
            initLeftMenuListData(str);
        }
        //取网络数据
        getNetInitData(ConstantsUtils.NEWS_URL);
    }


    public void getNetInitData(final String url) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                initLeftMenuListData(responseInfo.result);
                //保存数据作为缓存
                SpUtils.putString(mActivity, url, responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "onFailure: " + s);
            }
        });
    }

    private void initLeftMenuListData(String mStrResult) {
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(mStrResult, NewsBean.class);
        //传递left_menu初始化数据
        MainActivity mainActivity = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        leftMenuFragment.setInitData(newsBean.data);
    }

    public void changeMenuPager(int index) {
        fl_base_pager_content.removeAllViews();
        fl_base_pager_content.addView(mPagerList.get(index).rootView);
        //tv_title.setText();
    }
}
