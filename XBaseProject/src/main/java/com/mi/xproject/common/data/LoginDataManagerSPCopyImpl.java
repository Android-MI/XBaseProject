package com.mi.xproject.common.data;

import android.content.Context;

import com.mi.xproject.common.utils.SharedPreferenceAccesser;
import com.mi.xproject.dao.SharedPreferencesDao;

import org.json.JSONArray;

/**
 * APP数据存储实现类
 */
public class LoginDataManagerSPCopyImpl implements ILoginDataManager {
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

    private LoginDataManagerSPCopyImpl(Context context) {
        this.mContext = context;
    }

    public static ILoginDataManager getInstace(Context context) {
        if (mLoginDataManager == null) {
            mLoginDataManager = new LoginDataManagerSPCopyImpl(context);
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
        if (mContext == null) {
            return ;
        }
        // 注册成功后的状态保存
        SharedPreferencesDao.getInstance().saveData(LOGIN_STATE,
                state);
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
    public void deleteLoginState() {
        if (mContext == null) {
            return ;
        }

        SharedPreferenceAccesser.deleteData(mContext, LOGIN_STATE);

    }

    @Override
    public void setLoginUserType(String type) {
        boolean flag = false;
        if (mContext == null) {
            return ;
        }
        SharedPreferenceAccesser.saveStringData(mContext,
                LOGIN_USER_TYPE, type);

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
    public void deleteLoginUserType() {
        boolean flag = false;
        if (mContext == null) {
            return ;
        }

        SharedPreferenceAccesser.deleteData(mContext, LOGIN_USER_TYPE);

    }

    @Override
    public void setLoginUserInfo(String userInfo) {
        boolean flag = false;
        if (mContext == null) {
            return ;
        }
        SharedPreferenceAccesser.saveStringData(mContext,
                LOGIN_USER_INFO, userInfo);

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
    public void deleteLoginUserInfo() {
        boolean flag = false;
        if (mContext == null) {
            return ;
        }

        SharedPreferenceAccesser.deleteData(mContext, LOGIN_USER_INFO);

    }

}
