package com.mi.xproject.common.data;


/**
 * 基本登录信息数据存储、读取接口
 */
public interface ILoginDataManager {

    void setLoginState(boolean loginState);
    boolean getLoginState();
    void deleteLoginState();

    void setLoginUserType(String type);
    String getLoginUserType();
    void deleteLoginUserType();

    void setLoginUserInfo(String userInfo);
    String getUserInfo();
    void deleteLoginUserInfo();

}
