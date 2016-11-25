package com.example.linson.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/25.
 */

public class SpUtils {
    public static final String GUIDE_FLAG = "GUIDE";
    static SharedPreferences sp = null;

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences("zhbj", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();

    }

    public static boolean getBoolean(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences("zhbj", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, false);
    }
}
