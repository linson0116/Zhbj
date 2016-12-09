package com.example.linson.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.linson.zhbj.R;

/**
 * Created by Administrator on 2016/12/9.
 */

public class RefreshListView extends ListView {

    private LinearLayout mHeaderView;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    private void initHeaderView() {
        mHeaderView = (LinearLayout) View.inflate(getContext(), R.layout.refresh_listview_header, null);
        ImageView mIvArrow = (ImageView) mHeaderView.findViewById(R.id.iv_refresh_listview_header_arrow);
        ProgressBar mProgressbar = (ProgressBar) mHeaderView.findViewById(R.id.pb_refresh_listview_header);
        TextView tvState = (TextView) mHeaderView.findViewById(R.id.tv_refresh_listview_header_state);
        TextView tvLastUpdateTime = (TextView) mHeaderView.findViewById(R.id.tv_refresh_listview_header_last_update_time);

        // 下拉刷新头布局对象
        View mPullDownHeaderView = mHeaderView.findViewById(R.id.ll_pull_down_header_view);

        mPullDownHeaderView.measure(0, 0);
        int headerViewHeight = mPullDownHeaderView.getMeasuredHeight();

        // 隐藏头布局
        mPullDownHeaderView.setPadding(0, -headerViewHeight, 0, 0);

        this.addHeaderView(mHeaderView);
    }

    //下拉刷新
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }

    public void addSecondHeaderView(View secondHeaderView) {
        mHeaderView.addView(secondHeaderView);
    }
}
