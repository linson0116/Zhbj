package com.example.linson.zhbj.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.linson.zhbj.R;
import com.example.linson.zhbj.base.BaseFragment;
import com.example.linson.zhbj.base.BasePager;
import com.example.linson.zhbj.base.impl.HomePager;
import com.example.linson.zhbj.base.impl.NewsPager;
import com.example.linson.zhbj.base.impl.PolicyPager;
import com.example.linson.zhbj.base.impl.ServicePager;
import com.example.linson.zhbj.base.impl.SettingPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linson on 2016/11/26.
 */

public class MainFragment extends BaseFragment {
    @ViewInject(R.id.rg_bottom)
    RadioGroup rg_bottom;
    @ViewInject(R.id.vp_content)
    ViewPager vp_content;

    List<BasePager> pagerList = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.main_fragment, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        rg_bottom.check(R.id.rb_home);
        pagerList.add(new HomePager(mActivity));
        pagerList.add(new NewsPager(mActivity));
        pagerList.add(new ServicePager(mActivity));
        pagerList.add(new PolicyPager(mActivity));
        pagerList.add(new SettingPager(mActivity));

        vp_content.setAdapter(new ContentAdapter());
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = pagerList.get(position);
            View view = basePager.rootView;
            container.addView(view);
            basePager.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
