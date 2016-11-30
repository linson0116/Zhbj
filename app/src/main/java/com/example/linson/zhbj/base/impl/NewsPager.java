package com.example.linson.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.linson.zhbj.MainActivity;
import com.example.linson.zhbj.base.BasePager;
import com.example.linson.zhbj.bean.NewsBean;
import com.example.linson.zhbj.fragment.LeftMenuFragment;
import com.example.linson.zhbj.utils.ConstantsUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import static android.content.ContentValues.TAG;

/**
 * Created by linson on 2016/11/26.
 */

public class NewsPager extends BasePager {

    private String mStrResult;

    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tv_title.setText("新闻");
        ib_menu.setVisibility(View.GONE);
        TextView textView = new TextView(mActivity);
        textView.setText("新闻");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        fl_base_pager_content.addView(textView);

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
}
