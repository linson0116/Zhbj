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
    private String mStrResult;
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

        getNetInitData(ConstantsUtils.NEWS_URL);
    }


    public void getNetInitData(String url) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                mStrResult = responseInfo.result;
                Log.i(TAG, "onSuccess: " + mStrResult);
                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(mStrResult, NewsBean.class);
//                String str = newsBean.data.get(0).children.get(0).title;
//                Log.i(TAG, "onSuccess: " + str);
                MainActivity mainActivity = (MainActivity) mActivity;
                //传递left_menu初始化数据
                LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
                leftMenuFragment.setInitData(newsBean.data);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "onFailure: " + s);
            }
        });
    }

    public void changeMenuPager(int index) {
        fl_base_pager_content.removeAllViews();
        fl_base_pager_content.addView(mPagerList.get(index).rootView);
        //tv_title.setText();
    }
}
