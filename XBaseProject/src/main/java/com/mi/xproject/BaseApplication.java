package com.mi.xproject;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.mi.xproject.dao.SharedPreferencesDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
        initOkGo();
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
     * 初始化网络请求框架相关配置
     */
    private void initOkGo() {

        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Accept", "application/json; ");

        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //使用sp保持cookie，如果cookie不过期，则一直有效
//            builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
            //使用数据库保持cookie，如果cookie不过期，则一直有效
//            builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
            //使用内存保持cookie，app退出后，cookie消失
//            builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
            //全局的读取超时时间
            builder.readTimeout(30000, TimeUnit.MILLISECONDS);
            //全局的写入超时时间
            builder.writeTimeout(30000, TimeUnit.MILLISECONDS);
            //全局的连接超时时间
            builder.connectTimeout(30000, TimeUnit.MILLISECONDS);

            OkGo.getInstance().init(this)
                    .setOkHttpClient(builder.build())
                    .setCacheMode(CacheMode.NO_CACHE)
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    .addCommonHeaders(headers);
//                    .addCommonParams(params);
        } catch (Exception e) {
            e.printStackTrace();
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
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode 版本号
     * @return 判断结果
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 检测当前系统声音是否为正常模式
     *
     * @return 是否正常
     */
    public boolean isAudioNormal() {
        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
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
