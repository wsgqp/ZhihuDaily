package com.demo.ian.zhihu.mvp.interf;

/**
 * Created by Admin on 2016-9-14.
 */
public interface IViewNewsDetail<T> {
    void showProgress();
    void showDetail(T detailNews);
    void hideProgress();
    void showLoadFailed(String msg);
}
