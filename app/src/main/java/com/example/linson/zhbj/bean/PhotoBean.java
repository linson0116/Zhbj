package com.example.linson.zhbj.bean;

import java.util.List;

/**
 * Created by linson on 2016/12/19.
 */

public class PhotoBean {
    public String retcode;
    public Data data;

    public class Data {
        public String countcommenturl;
        public String more;
        public String title;
        public List<News> news;
        public List topic;
    }

    public class News {
        public String comment;
        public String commentlist;
        public String commenturl;
        public String id;
        public String largeimage;
        public String listimage;
        public String pubdate;
        public String smallimage;
        public String title;
        public String type;
        public String url;
    }
}
