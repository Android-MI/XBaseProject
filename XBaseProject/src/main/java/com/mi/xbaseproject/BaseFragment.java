package com.mi.xbaseproject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mi.xbaseproject.common.data.ILoginDataManager;
import com.mi.xbaseproject.common.data.LoginDataManagerSPImpl;


/**
 * 若把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 * -BaseActivity
 * -注1: 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 * ------可以调用mViewPager.setOffscreenPageLimit(size),若设置了该属性 则viewpager会缓存指定数量的Fragment
 * -注2: 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * -注3: 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {
    protected BaseActivity mBaseActivity = null;
    public ILoginDataManager mLoginDataManager = null;

    protected String fragmentTitle;             //fragment标题
    protected LayoutInflater inflater;
    private boolean isVisible;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;         //是否第一次加载

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }
    @Override
    public void onAttach(Context context) {
        mBaseActivity = (BaseActivity) context;
        mLoginDataManager = LoginDataManagerSPImpl
                .getInstace(mBaseActivity);
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity context) {
        mBaseActivity = (BaseActivity) context;
        mLoginDataManager = LoginDataManagerSPImpl
                .getInstace(mBaseActivity);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginDataManager = LoginDataManagerSPImpl
                .getInstace(mBaseActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
//        isFirstLoad = true;
//        View view = initView(inflater, container, savedInstanceState);
//        isPrepared = true;
//        lazyLoad();
//        return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        resetLayout(getView());
        super.onActivityCreated(savedInstanceState);
    }

    protected void baseAddFragment(int container, android.support.v4.app.Fragment fragment) {
        FragmentManager fragmentManager = getActivity()
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (fragment != null) {
            if (fragment.isAdded()) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.replace(container, fragment);
            }
        }
        fragmentTransaction.commit();

    }

    protected void onVisible() {
//        lazyLoad();
    }

    protected void onInvisible() {
    }

//    protected void lazyLoad() {
//        if (!isPrepared || !isVisible || !isFirstLoad) {
//            return;
//        }
//        isFirstLoad = false;
//        initData();
//    }

    protected abstract void resetLayout(View view);

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    // 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可

//    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    // 若把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment

//    protected abstract void initData();
    public String getTitle() {
        return TextUtils.isEmpty(fragmentTitle) ? "" : fragmentTitle;
    }

    public void setTitle(String title) {
        fragmentTitle = title;
    }

}
