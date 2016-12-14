package com.example.linson.zhbj.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.linson.zhbj.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsDetailUI extends Activity {
    @ViewInject(R.id.ib_back)
    ImageButton ib_back;
    @ViewInject(R.id.ib_text_size)
    ImageButton ib_text_size;
    @ViewInject(R.id.ib_share)
    ImageButton ib_share;
    @ViewInject(R.id.wv_news_detail)
    WebView wv_news_detail;
    @ViewInject(R.id.pb_news_detail)
    ProgressBar pb_news_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail_ui);
        ViewUtils.inject(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
//        Log.i(TAG, "initView: url " + url);
        wv_news_detail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pb_news_detail.setVisibility(View.GONE);
//                Log.i(TAG, "onPageFinished: ");
            }
        });
        WebSettings settings = wv_news_detail.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        wv_news_detail.loadUrl(url);
    }
}
