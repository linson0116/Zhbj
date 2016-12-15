package com.example.linson.zhbj.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

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
    private int mChooseWhich = 2;
    private WebSettings mSettings;

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
        wv_news_detail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pb_news_detail.setVisibility(View.GONE);
            }
        });
        mSettings = wv_news_detail.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setBuiltInZoomControls(true);
        mSettings.setUseWideViewPort(true);
        wv_news_detail.loadUrl(url);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib_text_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsDetailUI.this);
                builder.setTitle("AAA");
                String[] item = {"最小字体", "稍小字体", "正常字体", "稍大字体", "最大字体"};
                builder.setSingleChoiceItems(item, mChooseWhich, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChooseWhich = which;
                        Log.i(TAG, "onClick: " + mChooseWhich);
                    }
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (mChooseWhich) {
                            case 0:
                                mSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                                break;
                            case 1:
                                mSettings.setTextSize(WebSettings.TextSize.SMALLER);
                                break;
                            case 2:
                                mSettings.setTextSize(WebSettings.TextSize.NORMAL);
                                break;
                            case 3:
                                mSettings.setTextSize(WebSettings.TextSize.LARGER);
                                break;
                            case 4:
                                mSettings.setTextSize(WebSettings.TextSize.LARGEST);
                                break;
                        }
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
    }
}
