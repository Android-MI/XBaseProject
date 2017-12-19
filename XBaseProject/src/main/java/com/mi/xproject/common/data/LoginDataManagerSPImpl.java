package com.mi.xproject.common.data;

import android.content.Context;

import com.mi.xproject.dao.SharedPreferencesDao;

import org.json.JSONArray;

/**
 * APP数据存储实现类
 */
public class LoginDataManagerSPImpl implements ILoginDataManager {
    private static String preferencesName = SharedPreferencesDao.SharedPreferencesName;
    /**
     * 登录状态 KEY
     */
    private static final String LOGIN_STATE = "loginState";
    /**
     * 登录用户信息
     */
    private static final String LOGIN_USER_TYPE = "loginUserType";
    private static final String LOGIN_USER_INFO = "loginUserInfo";
    /**
     * 存储接口
     */
    private static ILoginDataManager mLoginDataManager = null;
    /**
     * Activity
     */
    private Context mContext = null;

    private LoginDataManagerSPImpl(Context context) {
        this.mContext = context;
    }

    public static ILoginDataManager getInstace(Context context, String preferenceName) {
        if (mLoginDataManager == null) {
            preferencesName = preferenceName;
            mLoginDataManager = new LoginDataManagerSPImpl(context);
        }
        return mLoginDataManager;
    }

    public static ILoginDataManager getInstace(Context context) {
        if (mLoginDataManager == null) {
            mLoginDataManager = new LoginDataManagerSPImpl(context);
        }
        return mLoginDataManager;
    }


    public static JSONArray getJsonArray(Context context) {
        JSONArray jsonArray = null;
        if (context == null) {
            return null;
        }

        return jsonArray;
    }

    @Override
    public void setLoginState(boolean state) {
        // 注册成功后的状态保存
        SharedPreferencesDao.getInstance(preferencesName).saveData(LOGIN_STATE,
                state);
    }

    @Override
    public boolean getLoginState() {
        // 获取登陆的状态值
        return SharedPreferencesDao.getInstance(preferencesName).getData(LOGIN_STATE, false, Boolean.class);
    }

    @Override
    public void deleteLoginState() {
        SharedPreferencesDao.getInstance(preferencesName).deleteData(LOGIN_STATE);
    }

    @Override
    public void setLoginUserType(String type) {
        SharedPreferencesDao.getInstance(preferencesName).saveData(
                LOGIN_USER_TYPE, type);
    }

    @Override
    public String getLoginUserType() {
        return SharedPreferencesDao.getInstance(preferencesName).getData(
                LOGIN_USER_TYPE, "", String.class);
    }

    @Override
    public void deleteLoginUserType() {
        SharedPreferencesDao.getInstance(preferencesName).deleteData(LOGIN_USER_TYPE);
    }

    @Override
    public void setLoginUserInfo(String userInfo) {
        SharedPreferencesDao.getInstance(preferencesName).saveData(
                LOGIN_USER_INFO, userInfo);
    }

    @Override
    public String getUserInfo() {
        return SharedPreferencesDao.getInstance(preferencesName).getData(
                LOGIN_USER_INFO, "", String.class);
    }

    @Override
    public void deleteLoginUserInfo() {
        SharedPreferencesDao.getInstance(preferencesName).deleteData(LOGIN_USER_INFO);

    }

}
