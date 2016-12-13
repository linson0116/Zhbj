package com.example.linson.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.linson.zhbj.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/12/9.
 */

public class RefreshListView extends ListView {

    private final int PULL_DOWN_REFRESH = 0; // 下拉刷新
    private final int RELEASE_REFRESH = 1; // 释放刷新
    private final int REFRESHING = 2; // 正在刷新中
    private LinearLayout mHeaderView;
    private int mDownY = -1;
    private int mHeaderViewHeight;
    private View mPullDownHeaderView;
    private View mSecondHeaderView;
    private int mCurrentState = PULL_DOWN_REFRESH; // 当前头布局的状态, 默认为: 下拉刷新

    private int mListViewYOnScreen = -1; // ListView在屏幕中y轴的值
    private TextView tvState;
    private ImageView mIvArrow;
    private ProgressBar mProgressbar;
    private TextView tvLastUpdateTime;

    private RotateAnimation upAnima; // 向上旋转的动画
    private RotateAnimation downAnima; // 向下旋转的动画

    private OnRefreshListener mOnRefreshListener; // 用户的回调事件
    private Boolean isLoadingMore = false;
    private View mFooterView;
    private int mFooterViewMeasuredHeight;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }

    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.refresh_listview_footer, null);
        this.addFooterView(mFooterView);
        mFooterView.measure(0, 0);
        mFooterViewMeasuredHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE ||
                        scrollState == SCROLL_STATE_FLING) {
                    if (getLastVisiblePosition() == getCount() - 1 && !isLoadingMore) {
                        Log.i(TAG, "onScrollStateChanged: 达到底部");
                        isLoadingMore = true;
                        mFooterView.setPadding(0, 0, 0, 0);
                        setSelection(getCount());
                        //用户回调方法
                        mOnRefreshListener.onLoadingMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initHeaderView() {
        mHeaderView = (LinearLayout) View.inflate(getContext(), R.layout.refresh_listview_header, null);
        mIvArrow = (ImageView) mHeaderView.findViewById(R.id.iv_refresh_listview_header_arrow);
        mProgressbar = (ProgressBar) mHeaderView.findViewById(R.id.pb_refresh_listview_header);
        tvState = (TextView) mHeaderView.findViewById(R.id.tv_refresh_listview_header_state);
        tvLastUpdateTime = (TextView) mHeaderView.findViewById(R.id.tv_refresh_listview_header_last_update_time);

        // 下拉刷新头布局对象
        mPullDownHeaderView = mHeaderView.findViewById(R.id.ll_pull_down_header_view);

        mPullDownHeaderView.measure(0, 0);
        mHeaderViewHeight = mPullDownHeaderView.getMeasuredHeight();

        // 隐藏头布局
        mPullDownHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

        this.addHeaderView(mHeaderView);

        initAnimation();
    }
    private void initAnimation() {
        upAnima = new RotateAnimation(
                0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        upAnima.setFillAfter(true);
        upAnima.setDuration(500);

        downAnima = new RotateAnimation(
                -180, -360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        downAnima.setFillAfter(true);
        downAnima.setDuration(500);
    }

    //下拉刷新
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int mMoveY = (int) ev.getY();
                if (mDownY == -1) {
                    mDownY = (int) ev.getY();
                }
                int diff = mMoveY - mDownY;
                // 判断当前是否正在刷新中
                if (mCurrentState == REFRESHING) {
                    // 当前正在刷新中, 不执行下拉, 直接跳出
                    break;
                }
                // 判断轮播图是否完全显示了
                boolean isDisplay = isDisplaySecondHeaderView();
                if (!isDisplay) {
                    // 没有完全显示, 不执行下拉头的操作, 直接跳出Switch
                    break;
                }
                if (diff > 0) {
                    int moveDistance = -mHeaderViewHeight + mMoveY - mDownY;
                    if (moveDistance > 0 && mCurrentState != RELEASE_REFRESH) { // 完全显示, 进入松开刷新状态
                        System.out.println("进入松开刷新状态");

                        mCurrentState = RELEASE_REFRESH;
                        refreshHeaderViewState();
                    } else if (moveDistance < 0 && mCurrentState != PULL_DOWN_REFRESH) { // 没有完全显示, 进入下拉刷新状态
                        System.out.println("进入下拉刷新状态");

                        mCurrentState = PULL_DOWN_REFRESH;
                        refreshHeaderViewState();
                    }
                    mPullDownHeaderView.setPadding(0, moveDistance, 0, 0);
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                mDownY = -1;
                if (mCurrentState == PULL_DOWN_REFRESH) {
                    //下拉刷新
                    mPullDownHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                } else if (mCurrentState == RELEASE_REFRESH) {
                    // 当前是释放刷新, 进入到正在刷新中的状态
                    mCurrentState = REFRESHING;
                    refreshHeaderViewState();

                    mPullDownHeaderView.setPadding(0, 0, 0, 0);

                    // 调用用户的回调事件, 刷新数据
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void addSecondHeaderView(View secondHeaderView) {
        mSecondHeaderView = secondHeaderView;
        mHeaderView.addView(secondHeaderView);
    }

    private boolean isDisplaySecondHeaderView() {
        // 获取ListView在屏幕中y轴的值
        int[] location = new int[2]; // 0位存储的是x轴的值, 1是y轴的值

        if (mListViewYOnScreen == -1) {
            this.getLocationOnScreen(location); // 传递给此方法一个数组, 会把数组中的0,1位填上x,y轴的值
            mListViewYOnScreen = location[1];
            System.out.println("ListView在屏幕中y轴的值: " + mListViewYOnScreen);
        }

        // 获取轮播图在屏幕中y轴的值
        mSecondHeaderView.getLocationOnScreen(location);
        int mSecondHeaderViewYOnScreen = location[1];
//		System.out.println("轮播图在屏幕中y轴的值: " + mSecondHeaderViewYOnScreen);

        return mListViewYOnScreen <= mSecondHeaderViewYOnScreen;
    }

    private void refreshHeaderViewState() {
        switch (mCurrentState) {
            case PULL_DOWN_REFRESH: // 下拉刷新
                mIvArrow.startAnimation(downAnima);
                tvState.setText("下拉刷新");
                break;
            case RELEASE_REFRESH: // 松开刷新
                mIvArrow.startAnimation(upAnima);
                tvState.setText("松开刷新");
                break;
            case REFRESHING: // 正在刷新中..
                mIvArrow.clearAnimation();
                mIvArrow.setVisibility(View.INVISIBLE);
                mProgressbar.setVisibility(View.VISIBLE);
                tvState.setText("正在刷新中..");
                break;
            default:
                break;
        }
    }

    /**
     * 当刷新完数据之后, 调用此方法. 把头布局隐藏
     */
    public void onRefreshFinish(boolean isSuccess) {
        if (isLoadingMore) {
            isLoadingMore = false;
            mFooterView.setPadding(0, -mFooterViewMeasuredHeight, 0, 0);

        } else {
            mPullDownHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
            mCurrentState = PULL_DOWN_REFRESH;
            mProgressbar.setVisibility(View.INVISIBLE);
            mIvArrow.setVisibility(View.VISIBLE);
            tvState.setText("下拉刷新");

            if (isSuccess) {
                // 最后刷新时间
                tvLastUpdateTime.setText("最后刷新时间: " + getCurrentTime());
            }
        }

    }

    /**
     * 获取当前时间: 1990-09-09 09:09:09
     *
     * @return
     */
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public interface OnRefreshListener {

        /**
         * 当下拉刷新时触发此方法
         */
        void onPullDownRefresh();

        //加载更多
        void onLoadingMore();


    }
}
