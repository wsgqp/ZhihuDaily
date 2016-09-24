package com.demo.ian.zhihu.utils;

/**
 * Created by Admin on 2016-9-9.
 */
public class API {
    public static final int TYPE_LATEST = 0;
    public static final int TYPE_BEFORE = 1;

    //知乎日报首页
    public static final String NEWS_LATEST = "http://news-at.zhihu.com/api/4/stories/latest";
    public static final String NEWS_BEFORE = "http://news-at.zhihu.com/api/4/stories/before/"; //格式：http://news-at.zhihu.com/api/4/stories/before/20160923
    public static final String NEWS_DETAIL = "http://news-at.zhihu.com/api/4/story/"; //格式：http://news-at.zhihu.com/api/4/story/8822705

    // 知乎日报主题
    public static final String CONST_THEMES = "http://news-at.zhihu.com/api/4/themes";
    public static final String CONST_THEME_ID = "http://news-at.zhihu.com/api/4/theme/"; //格式：http://news-at.zhihu.com/api/4/theme/11
    public static final String CONST_THEME_ID_BEFORE = "http://news-at.zhihu.com/api/4/theme/"; //格式：http://news-at.zhihu.com/api/4/theme/2/before/4705133
    public static final String CONST_THEME_ID_DETAIL = "http://news-at.zhihu.com/api/4/story/"; //格式：http://news-at.zhihu.com/api/4/story/8822705
}
