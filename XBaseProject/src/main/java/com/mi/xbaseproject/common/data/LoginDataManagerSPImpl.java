package com.mi.xbaseproject.common.data;

import android.content.Context;

import com.mi.xbaseproject.common.utils.SharedPreferenceAccesser;

import org.json.JSONArray;

/**
 * APP数据存储实现类
 */
public class LoginDataManagerSPImpl implements ILoginDataManager {
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
    public boolean setLoginState(boolean state) {
        boolean flag = false;
        if (mContext == null) {
            return false;
        }
        // 注册成功后的状态保存
        flag = SharedPreferenceAccesser.saveBooleanData(mContext, LOGIN_STATE,
                state);
        return flag;
    }

    @Override
    public boolean getLoginState() {
        boolean state = false;
        if (mContext == null) {
            return false;
        }
        // 获取登陆的状态值
        state = SharedPreferenceAccesser.getBooleanData(mContext, LOGIN_STATE);

        return state;
    }

    @Override
    public boolean deleteLoginState() {
        boolean flag = false;
        if (mContext == null) {
            return false;
        }

        flag = SharedPreferenceAccesser.deleteData(mContext, LOGIN_STATE);
        return flag;
    }

    @Override
    public boolean setLoginUserType(String type) {
        boolean flag = false;
        if (mContext == null) {
            return false;
        }
        flag = SharedPreferenceAccesser.saveStringData(mContext,
                LOGIN_USER_TYPE, type);
        return flag;

    }

    @Override
    public String getLoginUserType() {
        String time = "";
        if (mContext == null) {
            return null;
        }

        if (SharedPreferenceAccesser.getStringData(mContext, LOGIN_USER_TYPE) != null) {
            time = SharedPreferenceAccesser.getStringData(mContext,
                    LOGIN_USER_TYPE);
        }

        return time;
    }

    @Override
    public boolean deleteLoginUserType() {
        boolean flag = false;
        if (mContext == null) {
            return false;
        }

        flag = SharedPreferenceAccesser.deleteData(mContext, LOGIN_USER_TYPE);
        return flag;
    }

    @Override
    public boolean setLoginUserInfo(String userInfo) {
        boolean flag = false;
        if (mContext == null) {
            return flag;
        }
        flag = SharedPreferenceAccesser.saveStringData(mContext,
                LOGIN_USER_INFO, userInfo);
        return flag;
    }

    @Override
    public String getUserInfo() {
        String time = null;
        if (mContext == null) {
            return null;
        }

        if (SharedPreferenceAccesser.getStringData(mContext, LOGIN_USER_INFO) != null) {
            time = SharedPreferenceAccesser.getStringData(mContext,
                    LOGIN_USER_INFO);
        }

        return time;
    }

    @Override
    public boolean deleteLoginUserInfo() {
        boolean flag = false;
        if (mContext == null) {
            return flag;
        }

        flag = SharedPreferenceAccesser.deleteData(mContext, LOGIN_USER_INFO);
        return flag;
    }

}
