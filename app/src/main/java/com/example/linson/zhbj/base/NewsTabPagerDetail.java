package com.example.linson.zhbj.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.linson.zhbj.R;
import com.example.linson.zhbj.bean.NewsBean;
import com.example.linson.zhbj.bean.TabDetailBean;
import com.example.linson.zhbj.utils.ConstantsUtils;
import com.example.linson.zhbj.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewsTabPagerDetail {
    private final BitmapUtils mBitmapUtils;
    public Activity mActivity;
    public View rootView;
    public NewsBean.TitleBean titleBean;

    @ViewInject(R.id.vp_news_pic)
    ViewPager vp_news_pic;
    @ViewInject(R.id.tv_news_title)
    TextView tv_news_title;
    @ViewInject(R.id.ll_pic_points)
    LinearLayout ll_pic_points;
    @ViewInject(R.id.rlv_news)
    RefreshListView rlv_news;
    private TabDetailBean mTabDetailBean;
    private NewsListAdapter mNewsListAdapter;
    private List<TabDetailBean.News> mNewsListData;

    private boolean isPullDownRefresh = false; // 是否正在下拉刷新中

    public NewsTabPagerDetail(Activity activity, NewsBean.TitleBean titleBean, boolean isEnableSlidingMenu) {
        mActivity = activity;
        this.titleBean = titleBean;

        initView();
        mBitmapUtils = new BitmapUtils(mActivity);
        mBitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.news_tab_detail_pager, null);
        rootView = view;
        ViewUtils.inject(this, view);

        View news_top_pics = View.inflate(mActivity, R.layout.news_top_pics, null);
        ViewUtils.inject(this, news_top_pics);
        //添加到头部
        //lv_news.addHeaderView(news_top_pics);
        //添加轮播图
        rlv_news.addSecondHeaderView(news_top_pics);

        //回调
        rlv_news.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onPullDownRefresh() {
                isPullDownRefresh = true;
                initData();
            }
        });
        return rootView;
    }

    public void initData() {
        String url = ConstantsUtils.SERVER_URL + titleBean.url;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if (isPullDownRefresh) {
                    isPullDownRefresh = false;
                    rlv_news.onRefreshFinish(true);
                }
                processData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "onFailure: 请求数据失败");
                if (isPullDownRefresh) {
                    isPullDownRefresh = false;
                    rlv_news.onRefreshFinish(false);
                }
            }
        });


    }

    //处理json
    private void processData(String result) {
        Gson gson = new Gson();
        mTabDetailBean = gson.fromJson(result, TabDetailBean.class);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        vp_news_pic.setAdapter(myPagerAdapter);
        vp_news_pic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_news_title.setText(mTabDetailBean.data.topnews.get(position).title);
                Log.i(TAG, "onPageSelected: " + mTabDetailBean.data.topnews.get(position).title);

                int childCount = ll_pic_points.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = ll_pic_points.getChildAt(i);
                    childAt.setEnabled(false);
                }
                View view = ll_pic_points.getChildAt(position);
                view.setEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ll_pic_points.removeAllViews();
        for (int i = 0; i < mTabDetailBean.data.topnews.size(); i++) {
            View view = new View(mActivity);
            view.setBackgroundResource(R.drawable.topnews_point_bg);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i != 0) {
                layoutParams.leftMargin = 10;
                view.setEnabled(false);
            } else {
                view.setEnabled(true);
            }
            view.setLayoutParams(layoutParams);
            ll_pic_points.addView(view);
        }
        tv_news_title.setText(mTabDetailBean.data.topnews.get(0).title);
        //lv_news
        mNewsListData = mTabDetailBean.data.news;
        mNewsListAdapter = new NewsListAdapter();
        rlv_news.setAdapter(mNewsListAdapter);
    }

    class NewsListAdapter extends BaseAdapter {

        NewsListItemViewHolder holder;

        @Override
        public int getCount() {
            return mNewsListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.news_list_item, null);
                holder = new NewsListItemViewHolder();
                holder.iv_news_list_item_icon = (ImageView) convertView.findViewById(R.id.iv_news_list_item_icon);
                holder.tv_news_list_item_textdate = (TextView) convertView.findViewById(R.id.tv_news_list_item_textdate);
                holder.tv_news_list_item_textdetail = (TextView) convertView.findViewById(R.id.tv_news_list_item_textdetail);
                convertView.setTag(holder);
            }
            holder = (NewsListItemViewHolder) convertView.getTag();
            TabDetailBean.News bean = mNewsListData.get(position);
            mBitmapUtils.display(holder.iv_news_list_item_icon, bean.listimage);
            holder.tv_news_list_item_textdetail.setText(bean.title);
            holder.tv_news_list_item_textdate.setText(bean.pubdate);
            return convertView;
        }
    }

    class NewsListItemViewHolder {
        ImageView iv_news_list_item_icon;
        TextView tv_news_list_item_textdetail;
        TextView tv_news_list_item_textdate;
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mTabDetailBean.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(mActivity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.drawable.topnews_item_default);
            mBitmapUtils.display(iv, mTabDetailBean.data.topnews.get(position).topimage);
            container.addView(iv);
//            tv_news_title.setText(mTabDetailBean.data.topnews.get(position).title);
            Log.i(TAG, "instantiateItem: " + mTabDetailBean.data.topnews.get(position).title);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
