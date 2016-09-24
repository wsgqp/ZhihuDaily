package com.demo.ian.zhihu.mvp.interf;

/**
 * fragment or activity need to implement this to show news list.
 */
public interface IViewNewsList<T> {
    void showProgress();
    void hideProgress();
    void addNews(T news);
    void loadFailed(String msg);
}
