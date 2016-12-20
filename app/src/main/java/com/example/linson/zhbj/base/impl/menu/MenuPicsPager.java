package com.example.linson.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.linson.zhbj.R;
import com.example.linson.zhbj.base.BaseMenuPager;
import com.example.linson.zhbj.bean.PhotoBean;
import com.example.linson.zhbj.utils.BitmapCacheUtils;
import com.example.linson.zhbj.utils.ConstantsUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.linson.zhbj.utils.BitmapCacheUtils.BITMAP_FROM_NET;

/**
 * Created by Administrator on 2016/12/1.
 */

public class MenuPicsPager extends BaseMenuPager {
    public List<PhotoBean.News> mNewsPhotoList;
    public boolean isGridList = false;
    @ViewInject(R.id.lv_photos)
    ListView lv_photos;
    @ViewInject(R.id.gv_photos)
    GridView gv_photos;
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BITMAP_FROM_NET:
                    //取得网络图片
                    Log.i(TAG, "handleMessage: " + "取得网络图片");
                    int positon = msg.arg1;
                    ImageView iv = (ImageView) lv_photos.findViewWithTag(positon);
                    iv.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
        }
    };
    private BitmapCacheUtils mBitmapCacheUtils;

    public MenuPicsPager(Activity activity) {
        super(activity);
        mBitmapCacheUtils = new BitmapCacheUtils(mHandler);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.photos, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        String url = ConstantsUtils.PHOTO_URL;
        getDataFromNet(url);

    }

    private void getDataFromNet(String url) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                PhotoBean photoBean = gson.fromJson(responseInfo.result, PhotoBean.class);
                mNewsPhotoList = photoBean.data.news;
                NewsPhotoAdapter mNewsPhotoAdapter = new NewsPhotoAdapter();
                lv_photos.setAdapter(mNewsPhotoAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "onFailure: 联网失败");
            }
        });
    }

    public void changePhotoListType(ImageButton ib_grid_type) {
        if (isGridList) {
            isGridList = false;
            gv_photos.setVisibility(View.GONE);
            lv_photos.setVisibility(View.VISIBLE);
            ib_grid_type.setBackgroundResource(R.mipmap.icon_pic_grid_type);
        } else {
            isGridList = true;
            gv_photos.setVisibility(View.VISIBLE);
            lv_photos.setVisibility(View.GONE);
            ib_grid_type.setBackgroundResource(R.mipmap.icon_pic_list_type);
            gv_photos.setAdapter(new NewsPhotoAdapter());
        }
    }

    class MyHolder {
        ImageView iv_item_photo;
        TextView tv_item_text;
    }

    class NewsPhotoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNewsPhotoList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder myHolder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.news_photo_list_item, null);
                myHolder = new MyHolder();
                myHolder.iv_item_photo = (ImageView) convertView.findViewById(R.id.iv_item_photo);
                myHolder.tv_item_text = (TextView) convertView.findViewById(R.id.tv_item_text);
                convertView.setTag(myHolder);
            } else {
                myHolder = (MyHolder) convertView.getTag();
            }
            PhotoBean.News news = mNewsPhotoList.get(position);
            myHolder.tv_item_text.setText(news.title);
            myHolder.iv_item_photo.setTag(position);
//            BitmapUtils bitmapUtils = new BitmapUtils(mActivity);
//            bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);
//            bitmapUtils.display(myHolder.iv_item_photo, news.listimage);
            //图片三级缓存
            String url = news.listimage;

            Bitmap bitmap = mBitmapCacheUtils.getBitmap(url, position);
            if (bitmap != null) {
                myHolder.iv_item_photo.setImageBitmap(bitmap);
            }
            return convertView;
        }
    }
}
