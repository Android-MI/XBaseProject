package com.mi.xproject.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * 存储key-value 数据，支持加密
 * SharedPreferencesDao 就是操作SP
 */
@SuppressWarnings("unchecked")
public class SharedPreferencesDao {
    private static final String TAG = SharedPreferencesDao.class.getSimpleName();
    private static final String SharedPreferencesName = "XBP-MI";
    private static SharedPreferencesDao sharedPreferencesDao;

    private static Context mContext;   //init form application
    private SharedPreferences sharedPreferences;

    /**
     * init SharedPreferences in application,set mContext
     */
    public static void initSharePrefenceDao(Context applicationContext) {
        mContext = applicationContext;
    }

    /**
     * get the SharedPreferencesDao instance
     * @return sharedPreferencesDao
     */
    public static SharedPreferencesDao getInstance() {
        if (sharedPreferencesDao == null) {
            sharedPreferencesDao = new SharedPreferencesDao(Context.MODE_PRIVATE);
        }
        return sharedPreferencesDao;
    }

    /**
     * get the SharedPreferencesDao instance
     * @String preferenceName name
     * @return sharedPreferencesDao
     */
    public static SharedPreferencesDao getInstance(String preferenceName) {
        if (sharedPreferencesDao == null) {
            sharedPreferencesDao = new SharedPreferencesDao(Context.MODE_PRIVATE,preferenceName);
        }
        return sharedPreferencesDao;
    }

    /**
     * MODE_MULTI_PROCESS  will not work fine
     */
    private SharedPreferencesDao(int mode) {
        if (mContext != null) {
            sharedPreferences = mContext.getSharedPreferences(SharedPreferencesName, mode);
        } else {
            Log.e(TAG, "WARMIKNG! You must initSharePrefenceDao in your Application ！");
        }
    }

    /**
     * MODE_MULTI_PROCESS  will not work fine
     *
     * @param mode           Private
     * @param preferenceName 自定义名
     */
    private SharedPreferencesDao(int mode, String preferenceName) {
        if (mContext != null) {
            sharedPreferences = mContext.getSharedPreferences(preferenceName, mode);
        } else {
            Log.e(TAG, "WARMIKNG! You must initSharePrefenceDao in your Application ！");
        }
    }


    /**
     The best way of storing double values in SharedPreferences without losing precision is:
     Transform to bit representation to store it as long:
     prefsEditor.putLong("Latitude", Double.doubleToLongBits(location.getLatitude()));
     To retrieve, transfrom from bit representation to double:
     double latitude = Double.longBitsToDouble(prefs.getLong("Latitude", 0);
     */

    /**
     * if type of defValue is not equal clazz
     *
     * @param key
     * @param defValue
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getData(String key, @NonNull Object defValue, @NonNull Class<T> clazz) {
        T t = null;
        if (!defValue.getClass().getSimpleName().equals(clazz.getSimpleName())) {
            // TODO: 2017/12/12 转换将失败！
            throw new UnsupportedOperationException("defValue type does not equals whit clazz ");
        }

        switch (clazz.getSimpleName()) {
            case "String":
                t = (T) sharedPreferences.getString(key, (String) defValue);
                break;
            case "Integer":
                t = (T) (Integer) sharedPreferences.getInt(key, (Integer) defValue);
                break;
            case "Float":
                t = (T) (Float) sharedPreferences.getFloat(key, (Float) defValue);
                break;
            case "Long":
                t = (T) (Long) sharedPreferences.getLong(key, (Long) defValue);
                break;
            case "Boolean":
                t = (T) (Boolean) sharedPreferences.getBoolean(key, (Boolean) defValue);
                break;
        }
        return t;
    }


    /**
     * 获取存入的数据信息
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String key, Object defaultObject) {

        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }


    /**
     * 存储数据
     *
     * @param key
     * @param value
     */
    public void saveData(String key, @NonNull Object value) {
        if (value instanceof String) {
            sharedPreferences.edit().putString(key, (String) value).commit();
        } else if (value instanceof Integer) {
            sharedPreferences.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof Float) {
            sharedPreferences.edit().putFloat(key, (Float) value).commit();
        } else if (value instanceof Long) {
            sharedPreferences.edit().putLong(key, (Long) value).commit();
        } else if (value instanceof Boolean) {
            sharedPreferences.edit().putBoolean(key, (Boolean) value).commit();
        }
    }

    public void deleteData(String key) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(key).commit();
        }
    }

    /**
     * 清空数据
     * @return 清空结果
     */
    public boolean clearAllData() {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            return editor.clear().commit();
        } else {
            Log.e(TAG, "WARMIKNG! You must initSharePrefenceDao && SharedPreferencesDao in your Application ！");
            return  false;
        }
    }

}
