package com.example.linson.zhbj;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.example.linson.zhbj.fragment.LeftMenuFragment;
import com.example.linson.zhbj.fragment.MainFragment;
import com.example.linson.zhbj.utils.ConstantsUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    private static final String TAG = "Content";
    private SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //sliding
        setBehindContentView(R.layout.sliding_left);
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setBehindOffset(300);

        android.support.v4.app.FragmentManager supportFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.replace(R.id.activity_main, new MainFragment(), ConstantsUtils.MAIN_FRAGMENT);
        ft.replace(R.id.fl_left_menu, new LeftMenuFragment(), ConstantsUtils.LEFT_MENU_FRAGMENT);
        ft.commit();
    }

    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LeftMenuFragment lmf = (LeftMenuFragment) fm.findFragmentByTag(ConstantsUtils.LEFT_MENU_FRAGMENT);
        return lmf;
    }

    public MainFragment getMainFragment() {
        FragmentManager fm = getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) fm.findFragmentByTag(ConstantsUtils.MAIN_FRAGMENT);
        return mainFragment;
    }
}
