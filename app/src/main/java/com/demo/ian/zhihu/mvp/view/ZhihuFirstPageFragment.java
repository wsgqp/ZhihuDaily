package com.demo.ian.zhihu.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.ian.zhihu.BaseFragment;
import com.demo.ian.zhihu.R;
import com.demo.ian.zhihu.adapter.ZhihuFirstPageAdapter;
import com.demo.ian.zhihu.mvp.domain.ZhihuFirstPageList;
import com.demo.ian.zhihu.mvp.interf.IPresenterList;
import com.demo.ian.zhihu.mvp.interf.IViewNewsList;
import com.demo.ian.zhihu.mvp.interf.OnListFragmentInteract;
import com.demo.ian.zhihu.mvp.presnter.ZhihuFirstPagePresenter;
import com.demo.ian.zhihu.utils.API;
import com.demo.ian.zhihu.utils.Constants;
import com.demo.ian.zhihu.utils.SnackbarUtil;

public class ZhihuFirstPageFragment extends BaseFragment implements IViewNewsList<ZhihuFirstPageList>, OnListFragmentInteract {
    protected View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    ZhihuFirstPageAdapter adapter;
    IPresenterList presenter;
    String date;
    boolean refresh = false; //是否重新加载，如果重新加载，以前的数据将全部清除

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_zhihu, container, false);
            swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ZhihuFirstPageAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastPosition = layoutManager.findLastVisibleItemPosition();
                        if (lastPosition + 1 == adapter.getItemCount()) {
                            refresh = false;
                            presenter.LoadZhihuStoryList(API.NEWS_BEFORE + date);
                        }
                    }
                }
            });
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh = true; //重新加载
                    presenter.LoadZhihuStoryList(API.NEWS_LATEST);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new ZhihuFirstPagePresenter(this);
        presenter.LoadZhihuStoryList(API.NEWS_LATEST);
    }

    @Override
    public void showProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置组件的刷洗状态。
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置组件的刷洗状态。
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void addNews(final ZhihuFirstPageList news) {
        date = news.getDate();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.OnUpdate(news, refresh);
            }
        });
    }

    @Override
    public void loadFailed(String msg) {
        SnackbarUtil.show(rootView, "加载失败：" + msg, 1);
    }

    @Override
    public void onListFragmentInteraction(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ZhihuFirstPageAdapter.ViewHolder) {
            ZhihuFirstPageAdapter.ViewHolder holder = (ZhihuFirstPageAdapter.ViewHolder) viewHolder;
            Intent intent = new Intent(getActivity(), ZhihuDetailActivity.class);
            intent.putExtra(Constants.ID, holder.zhihuStory.getId());
            startActivity(intent);
            holder.mTitle.setTextColor(ZhihuFirstPageAdapter.textGrey);
        } else if (viewHolder instanceof ZhihuFirstPageAdapter.BannerHolder) {
            ZhihuFirstPageAdapter.BannerHolder holder = (ZhihuFirstPageAdapter.BannerHolder) viewHolder;
            Intent intent = new Intent(getActivity(), ZhihuDetailActivity.class);
            intent.putExtra(Constants.ID, holder.zhihuTops.get(holder.banner.getCurrentItem()).getId());
            startActivity(intent);
            holder.news_title.get(holder.banner.getCurrentItem()).setTextColor(ZhihuFirstPageAdapter.textGrey);
        }
    }
}
