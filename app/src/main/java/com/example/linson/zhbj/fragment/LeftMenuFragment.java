package com.example.linson.zhbj.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.linson.zhbj.MainActivity;
import com.example.linson.zhbj.R;
import com.example.linson.zhbj.base.BaseFragment;
import com.example.linson.zhbj.base.impl.NewsPager;
import com.example.linson.zhbj.bean.NewsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class LeftMenuFragment extends BaseFragment {

    private List<NewsBean.MenuBean> mList;
    private ListView mListView;
    private MenuAdapter menuAdapter;
    private int currentSelectItem = 0;

    @Override
    public View initView() {
        mListView = new ListView(mActivity);
        mListView.setBackgroundColor(Color.BLACK);
        mListView.setPadding(0, 140, 0, 0);
        mListView.setDivider(null);
        mListView.setSelector(android.R.color.transparent);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSelectItem = position;
                menuAdapter.notifyDataSetChanged();
                MainActivity mainActivity = (MainActivity) mActivity;
                mainActivity.getSlidingMenu().toggle();
                NewsPager newsPager = (NewsPager) mainActivity.getMainFragment().pagerList.get(1);
                newsPager.changeMenuPager(position);
            }
        });
        return mListView;
    }

    public void setInitData(List list) {
        mList = list;
        setAdapter(mList);
    }

    private void setAdapter(List mList) {
        menuAdapter = new MenuAdapter();
        if (mListView == null) {
            initView();
        }
        mListView.setAdapter(menuAdapter);
    }

    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = (TextView) View.inflate(mActivity, R.layout.left_menu_item, null);
            tv.setText(mList.get(position).title);
            tv.setEnabled(position == currentSelectItem);
            return tv;
        }
    }
}
