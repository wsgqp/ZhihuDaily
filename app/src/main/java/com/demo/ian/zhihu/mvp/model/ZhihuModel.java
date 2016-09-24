package com.demo.ian.zhihu.mvp.model;

import com.demo.ian.zhihu.mvp.interf.OnLoadDataListener;
import com.demo.ian.zhihu.utils.API;
import com.demo.ian.zhihu.utils.GsonUtil;
import com.demo.ian.zhihu.utils.LogUtil;
import com.demo.ian.zhihu.utils.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Admin on 2016-9-12.
 */
public class ZhihuModel {
    public void getNewsList(String url, final OnLoadDataListener listener) {

        OkHttpUtil.GetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("error" + e.getLocalizedMessage());
                listener.onFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                LogUtil.v("sucess:" + body);
                listener.onSuccess(GsonUtil.parseZhihuJson(body));
            }
        });
    }

    public void getNewsDetail(int id, final OnLoadDataListener listener) {
        OkHttpUtil.GetRequest(API.NEWS_DETAIL + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("error" + e.getLocalizedMessage());
                listener.onFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                LogUtil.v("sucess:" + body);
                listener.onSuccess(GsonUtil.parseZhihuDetail(body));
            }
        });
    }

    /**
     * 获取知乎日报所有主题
     * @param listener
     */
    public void getThemes(final OnLoadDataListener listener){
        OkHttpUtil.GetRequest(API.CONST_THEMES, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("error" + e.getLocalizedMessage());
                listener.onFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                LogUtil.v("sucess:" + body);
                listener.onSuccess(GsonUtil.parseZhihuThemes(body));
            }
        });
    }

    public void getThemeList(String url, final OnLoadDataListener listener){
        OkHttpUtil.GetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("error" + e.getLocalizedMessage());
                listener.onFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                LogUtil.v("sucess:" + body);
                listener.onSuccess(GsonUtil.parseZhihuTheme(body));
            }
        });
    }
}
