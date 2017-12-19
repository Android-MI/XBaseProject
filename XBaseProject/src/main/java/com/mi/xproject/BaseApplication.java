package com.mi.xproject;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.mi.xproject.dao.SharedPreferencesDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局应用程序类
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance = null;
    private List<BaseActivity> mActivityList = null;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initConfig();
        initPreferenceName();
    }


    protected void initConfig() {
    }

    protected void initPreferenceName() {
        // 设置 SharePreference存储 Key
        String processName = getProcessName();  //注意区分进程初始化不同的东西
        if (!TextUtils.isEmpty(processName) && processName.equals(this.getPackageName())) { //main Process
            SharedPreferencesDao.initSharePrefenceDao(this);
        }
    }

    /**
     * 获取进程名
     *
     * @return 进程名
     */
    public String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检测网络是否可用
     *
     * @return 是否连接正常
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取安装包信息
     *
     * @return 安装包信息
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    public void add(BaseActivity baseActivity) {
        if (mActivityList == null) {
            mActivityList = new ArrayList<>();
        }
        mActivityList.add(baseActivity);
    }

    public void remove(BaseActivity activity) {
        if (mActivityList == null) {
            return;
        }
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    public void finishAll() {
        for (int i = 0; i < mActivityList.size(); i++) {
            mActivityList.get(i).finish();
        }
        mActivityList.clear();
    }

    public List<BaseActivity> getActivityList() {
        return mActivityList;
    }

}
