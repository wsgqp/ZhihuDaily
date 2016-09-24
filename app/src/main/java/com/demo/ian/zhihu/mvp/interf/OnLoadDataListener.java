package com.demo.ian.zhihu.mvp.interf;

/**
 * Created by Admin on 2016-9-9.
 */
public interface OnLoadDataListener<T> {
    void onSuccess(T obj);
    void onFailure(String msg);
}
