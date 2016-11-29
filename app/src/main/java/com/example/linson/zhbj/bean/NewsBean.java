package com.example.linson.zhbj.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class NewsBean {
    public List<MenuBean> data;
    public List<String> extend;
    public String retcode;

    public class MenuBean {
        public String id;
        public String title;
        public String type;
        public String url;
        public String url1;
        public String dayurl;
        public String excurl;
        public String weekurl;
        public List<TitleBean> children;
    }

    public class TitleBean {
        public String id;
        public String title;
        public String type;
        public String url;
    }
}
