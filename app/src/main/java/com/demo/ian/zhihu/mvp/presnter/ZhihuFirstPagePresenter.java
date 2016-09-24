package com.demo.ian.zhihu.mvp.presnter;

import com.demo.ian.zhihu.mvp.domain.ZhihuFirstPageList;
import com.demo.ian.zhihu.mvp.interf.IPresenterList;
import com.demo.ian.zhihu.mvp.interf.IViewNewsList;
import com.demo.ian.zhihu.mvp.interf.OnLoadDataListener;
import com.demo.ian.zhihu.mvp.model.ZhihuModel;

/**
 * Created by Admin on 2016-9-9.
 */
public class ZhihuFirstPagePresenter implements IPresenterList {

    private IViewNewsList<ZhihuFirstPageList> view;
    private ZhihuModel model;

    public ZhihuFirstPagePresenter(IViewNewsList<ZhihuFirstPageList> view) {
        this.view = view;
        model = new ZhihuModel();
    }

    @Override
    public void LoadZhihuStoryList(String url) {
        view.showProgress();
        model.getNewsList(url, listener);
    }

    OnLoadDataListener listener = new OnLoadDataListener<ZhihuFirstPageList>() {
        @Override
        public void onSuccess(ZhihuFirstPageList zhihuFirstPageList) {
            view.addNews(zhihuFirstPageList);
            view.hideProgress();
        }

        @Override
        public void onFailure(String msg) {
            view.loadFailed(msg);
            view.hideProgress();
        }
    };
}
