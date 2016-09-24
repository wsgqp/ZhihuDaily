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
import com.demo.ian.zhihu.adapter.ZhihuThemeAdapter;
import com.demo.ian.zhihu.mvp.domain.ZhihuStory;
import com.demo.ian.zhihu.mvp.domain.ZhihuThemeList;
import com.demo.ian.zhihu.mvp.interf.IPresenterList;
import com.demo.ian.zhihu.mvp.interf.IViewNewsList;
import com.demo.ian.zhihu.mvp.interf.OnListFragmentInteract;
import com.demo.ian.zhihu.mvp.presnter.ZhihuThemePresenter;
import com.demo.ian.zhihu.utils.API;
import com.demo.ian.zhihu.utils.Constants;
import com.demo.ian.zhihu.utils.SnackbarUtil;

public class ZhihuThemeFragment extends BaseFragment implements IViewNewsList<ZhihuThemeList>, OnListFragmentInteract {
    protected View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    ZhihuThemeAdapter adapter;
    IPresenterList presenter;
    String date;
    boolean refresh = false; //是否重新加载，如果重新加载，以前的数据将全部清除
    public int ThemeID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_zhihu, container, false);
            swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ZhihuThemeAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastPosition = layoutManager.findLastVisibleItemPosition();
                        if (lastPosition + 1 == adapter.getItemCount()) {
                            refresh = false;
                            ZhihuStory story = (ZhihuStory) adapter.zhihuStories.get(adapter.zhihuStories.size()-1);
                            presenter.LoadZhihuStoryList(API.CONST_THEME_ID_BEFORE + ThemeID + "/before/" + story.getId());
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
        presenter = new ZhihuThemePresenter(this);
        ThemeID = getArguments().getInt("id");
        presenter.LoadZhihuStoryList(API.CONST_THEME_ID + ThemeID);
    }

    public void ChangeTheme(int id) {
        if (id == ThemeID)
            refresh = false;
        else {
            refresh = true;
            ThemeID = id;
            presenter.LoadZhihuStoryList(API.CONST_THEME_ID + ThemeID);
        }
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
    public void addNews(final ZhihuThemeList news) {
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
        if (viewHolder instanceof ZhihuThemeAdapter.ViewHolder) {
            ZhihuThemeAdapter.ViewHolder holder = (ZhihuThemeAdapter.ViewHolder) viewHolder;
            Intent intent = new Intent(getActivity(), ZhihuDetailActivity.class);
            intent.putExtra(Constants.ID, holder.zhihuStory.getId());
            startActivity(intent);
            holder.mTitle.setTextColor(ZhihuThemeAdapter.textGrey);
        }
    }
}
