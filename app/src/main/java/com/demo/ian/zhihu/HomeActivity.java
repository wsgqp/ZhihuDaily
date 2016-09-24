package com.demo.ian.zhihu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.demo.ian.zhihu.mvp.domain.ZhihuThemes;
import com.demo.ian.zhihu.mvp.interf.OnLoadDataListener;
import com.demo.ian.zhihu.mvp.model.ZhihuModel;
import com.demo.ian.zhihu.mvp.view.ZhihuFirstPageFragment;
import com.demo.ian.zhihu.mvp.view.ZhihuThemeFragment;
import com.demo.ian.zhihu.utils.SnackbarUtil;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ZhihuFirstPageFragment firstPageFragment;
    ZhihuThemeFragment themeFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        firstPageFragment = (ZhihuFirstPageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_zhihu);

        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        setTitle("首页"); //setSupportActionBar(toolbar);该句代码会将WindowTitle设置为toolbar的Title，故先改变WindowTitle

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // 动态给navigationView添加Menu
        final Menu menu = navigationView.getMenu();
        menu.clear();
        menu.add(0, 0, 0, "首页").setIcon(R.mipmap.ic_launcher);
        new ZhihuModel().getThemes(new OnLoadDataListener<ZhihuThemes>() {
            @Override
            public void onSuccess(final ZhihuThemes obj) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (ZhihuThemes.Other other : obj.getOthers()) {
                            menu.add(0, other.getId(), other.getId(), other.getName());
                        }
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                SnackbarUtil.show(navigationView, "加载知乎主题失败：" + msg, 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        setTitle(item.getTitle());
        if ((item.getItemId() == 0) && (item.getTitle() == "首页")) {
            if (themeFragment != null) {
                fragmentManager.beginTransaction().hide(themeFragment).commit();
            }
            fragmentManager.beginTransaction().show(firstPageFragment).commit();
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("id", item.getItemId());
            if (themeFragment == null) {
                themeFragment = new ZhihuThemeFragment();
                fragmentManager.beginTransaction().hide(firstPageFragment).add(R.id.action_bar_container, themeFragment).commit();
                // 传递主题ID
                bundle.putInt("id", item.getItemId());
                themeFragment.setArguments(bundle);
            } else {
                themeFragment.ChangeTheme(item.getItemId());
                fragmentManager.beginTransaction().hide(firstPageFragment).show(themeFragment).commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
