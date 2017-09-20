package com.mi.xbaseproject.common.data;


/**
 * 数据存储、读取接口
 */
public interface ILoginDataManager {

    boolean setLoginState(boolean loginState);
    boolean getLoginState();
    boolean deleteLoginState();

    boolean setLoginUserType(String type);
    String getLoginUserType();
    boolean deleteLoginUserType();

    boolean setLoginUserInfo(String userInfo);
    String getUserInfo();
    boolean deleteLoginUserInfo();

}
