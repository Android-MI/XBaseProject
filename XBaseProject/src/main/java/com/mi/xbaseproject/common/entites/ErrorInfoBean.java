package com.mi.xbaseproject.common.entites;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaomi on 2017/8/25.
 */

public class ErrorInfoBean {

    /**
     * code : 403
     * message : 没有该骑手！
     */
    public int code;
    public String message;

    public static ErrorInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, ErrorInfoBean.class);
    }

    public static List<ErrorInfoBean> arrayErrorInfoBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ErrorInfoBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
