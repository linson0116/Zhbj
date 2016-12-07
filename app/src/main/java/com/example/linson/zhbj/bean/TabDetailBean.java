package com.example.linson.zhbj.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class TabDetailBean {
    public String retcode;
    public TabDetailData data;

    public class TabDetailData {
        public String countcommenturl;
        public String more;
        public String title;
        public List<News> news;
        public List<Topic> topic;
        public List<TopNew> topnews;
    }

    public class News {
        public String comment;
        public String commentlist;
        public String commenturl;
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }

    public class Topic {
        public String description;
        public String id;
        public String listimage;
        public String sort;
        public String title;
        public String url;
    }

    public class TopNew {
        public String comment;
        public String commentlist;
        public String commenturl;
        public String id;
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;
    }
}
