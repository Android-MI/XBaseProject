package com.mi.xpdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import com.mi.xpdemo.common.base.CommonActivity;
import com.mi.xpdemo.fragment.DemoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends CommonActivity {


    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.viewPager_main)
    ViewPager viewPager;
    @BindView(R.id.tab_main)
    TabLayout tab;

    private List<Pair<String, Fragment>> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(toolbar, false, "");

        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("Demo1", new DemoFragment()));
        items.add(new Pair<String, Fragment>("Demo2", new DemoFragment()));

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.fab_main)
    public void fab(View view) {

        WebActivity.runActivity(this, "BaiDu", "http://www.baidu.com");

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void resetLayout() {
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    private class MainAdapter extends FragmentPagerAdapter {

        MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position).second;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).first;
        }
    }
}
