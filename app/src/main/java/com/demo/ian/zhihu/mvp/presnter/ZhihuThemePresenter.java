package com.demo.ian.zhihu.mvp.presnter;

import com.demo.ian.zhihu.mvp.domain.ZhihuThemeList;
import com.demo.ian.zhihu.mvp.interf.IPresenterList;
import com.demo.ian.zhihu.mvp.interf.IViewNewsList;
import com.demo.ian.zhihu.mvp.interf.OnLoadDataListener;
import com.demo.ian.zhihu.mvp.model.ZhihuModel;

/**
 * Created by Admin on 2016-9-9.
 */
public class ZhihuThemePresenter implements IPresenterList {

    private IViewNewsList<ZhihuThemeList> view;
    private ZhihuModel model;

    public ZhihuThemePresenter(IViewNewsList<ZhihuThemeList> view) {
        this.view = view;
        model = new ZhihuModel();
    }

    @Override
    public void LoadZhihuStoryList(String url) {
        view.showProgress();
        model.getThemeList(url, listener);
    }

    OnLoadDataListener listener = new OnLoadDataListener<ZhihuThemeList>() {
        @Override
        public void onSuccess(ZhihuThemeList zhihuThemeList) {
            view.addNews(zhihuThemeList);
            view.hideProgress();
        }

        @Override
        public void onFailure(String msg) {
            view.loadFailed(msg);
            view.hideProgress();
        }
    };
}
