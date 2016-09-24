package com.demo.ian.zhihu.mvp.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.demo.ian.zhihu.R;
import com.demo.ian.zhihu.mvp.domain.ZhihuDetail;
import com.demo.ian.zhihu.mvp.interf.IPresenterNewsDetail;
import com.demo.ian.zhihu.mvp.interf.IViewNewsDetail;
import com.demo.ian.zhihu.mvp.presnter.ZhihuDetailPresenter;
import com.demo.ian.zhihu.utils.Constants;
import com.demo.ian.zhihu.utils.Imager;
import com.demo.ian.zhihu.utils.SnackbarUtil;

public class ZhihuDetailActivity extends AppCompatActivity implements IViewNewsDetail<ZhihuDetail> {

    private WebView webView;
    private ProgressBar progressBar;
    private ImageView detailImg;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private IPresenterNewsDetail presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_detail);

        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        detailImg = (ImageView) findViewById(R.id.detailImg);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        presenter = new ZhihuDetailPresenter(this);

        initData();
    }

    private void initData() {
        initWebView();
        int id = getIntent().getIntExtra(Constants.ID, 0);
        presenter.loadNewsDetail(id);
    }

    private void initWebView() {
        webView.setVisibility(View.INVISIBLE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.VISIBLE);
                            hideProgress();
                        }
                    }, 300);
                }
            }
        });
    }

    @Override
    public void showDetail(final ZhihuDetail detailNews) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Imager.load(ZhihuDetailActivity.this, detailNews.getImage(), detailImg);
                collapsingToolbarLayout.setTitle(detailNews.getTitle());
                //add css style to webView
                String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
                String html = "<html><head>" + css + "</head><body>" + detailNews.getBody() + "</body></html>";
                html = html.replace("<div class=\"img-place-holder\">", "");
                webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
            }
        });
    }

    @Override
    public void showLoadFailed(String msg) {
        SnackbarUtil.show(webView, "加载失败：" + msg, 1);
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
