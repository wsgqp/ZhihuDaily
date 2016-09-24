package com.demo.ian.zhihu.mvp.presnter;

import com.demo.ian.zhihu.mvp.domain.ZhihuDetail;
import com.demo.ian.zhihu.mvp.interf.IPresenterNewsDetail;
import com.demo.ian.zhihu.mvp.interf.IViewNewsDetail;
import com.demo.ian.zhihu.mvp.interf.OnLoadDataListener;
import com.demo.ian.zhihu.mvp.model.ZhihuModel;

/**
 * Created by Admin on 2016-9-13.
 */
public class ZhihuDetailPresenter implements IPresenterNewsDetail {

    private IViewNewsDetail<ZhihuDetail> view;
    private ZhihuModel model;

    public ZhihuDetailPresenter(IViewNewsDetail<ZhihuDetail> view) {
        this.view = view;
        model = new ZhihuModel();
    }

    @Override
    public void loadNewsDetail(int id) {
        view.showProgress();
        model.getNewsDetail(id, new OnLoadDataListener<ZhihuDetail>() {
            @Override
            public void onSuccess(ZhihuDetail detailNews) {
                view.showDetail(detailNews);
            }

            @Override
            public void onFailure(String msg) {
                view.hideProgress();
                view.showLoadFailed(msg);
            }
        });
    }
}
