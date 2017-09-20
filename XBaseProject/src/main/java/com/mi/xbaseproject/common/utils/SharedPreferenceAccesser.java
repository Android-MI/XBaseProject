package com.mi.xbaseproject.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 手机内存储存数据
 */
public class SharedPreferenceAccesser {

    private static String PREFERENCE_NAME = "XBaseProject";
    private static SharedPreferences mSP = null;

    public static SharedPreferences getInstace(Context context, String preferenceName) {
        if (mSP == null) {
            mSP = context.getSharedPreferences(preferenceName,
                    Context.MODE_PRIVATE);
        }
        return mSP;
    }

    public static boolean saveStringData(Context context, String key,
                                         String value) {
        if (context == null || key == null || value == null) {
            return false;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSP.edit();
//        editor.putString(key, AESCrypt.encrypt(value));
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean saveBooleanData(Context context, String key,
                                          boolean value) {
        if (context == null || key == null) {
            return false;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSP.edit();
        editor.putBoolean(key, value);
        return editor.commit();

    }

    public static boolean getBooleanData(Context context, String key) {
        boolean value = false;
        if (context == null || key == null) {
            return value;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        value = mSP.getBoolean(key, false);
        return value;

    }

    public static String getStringData(Context context, String key) {
        String value = null;
        if (context == null || key == null) {
            return value;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        if (mSP.getString(key, null) != null) {
//            value = AESCrypt.decrypt(mSP.getString(key, null));
            value = mSP.getString(key, null);
        }
        return value;

    }

    public static boolean deleteData(Context context, String key) {
        if (context == null || key == null) {
            return false;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSP.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean clearAllData(Context context) {
        if (context == null) {
            return false;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSP.edit();
        return editor.clear().commit();

    }

}
