package com.mi.xpdemo.common;

import android.os.Environment;

public class AppConstants {
    public static final String WECHAT_APP_ID = "";
    public static final String WECHAT_APP_Secret = "";
    public static final String APP_PERFERENCES_NAME = "XBP-DEMO";
    /**
     * 有些希望能缓存在app 的内部私有目录，随着app 的卸载一起删除
     *   /data/data: context.getFileDir().getPath();
     *   是一个应用程序的私有目录，只有当前应用程序有权限访问读写，其他应用无权限访问。一些安全性要求比较高的数据存放在该目录，一般用来存放size比较小的数据。
     *   sdcard:  Enviroment.getExternalStorageDirectory().getPath();
     *   是一个外部存储目录，只用应用声明了<uses-permission Android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>的一个权限，
     *   就可以访问读写sdcard目录；所以一般用来存放一些安全性不高的数据，文件size比较大的数据。
     */
    public static final String CACHE_HOME_PARENT = Environment.getExternalStorageDirectory() + "/xbp_mi";
}
