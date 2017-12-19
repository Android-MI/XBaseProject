package com.mi.xproject.common.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mi.utils.CCLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * 字符串操作工具包
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static String REPLACE_PATTERN = "＜br\\s*/?＞|＜p\\s*/?＞|[\\s\\n]";

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static String replaceBlank(String source) {
        String result = null;
        if (!Utils.isStringEmpty(source)) {
            result = source.replaceAll(REPLACE_PATTERN, "");
            CCLog.e("replace All Blank:", result);
        } else {
            result = null;
        }

        return result;
    }

    public static JSONObject toJSONObject(String json) throws JSONException {
        if (!isEmpty(json)) {
            if (json.indexOf("{") == 0) {
                json = json.substring(1, json.length());
            }
            if (json.lastIndexOf("}") == json.length()) {
                json = json.substring(0, json.length() - 1);
            }
            return new JSONObject(json);
        }
        return null;
    }

    public static JSONArray toJSONArray(String json) throws JSONException {
        if (!isEmpty(json)) {
            if (json.indexOf("[") == 0) {
                json = json.substring(1, json.length());
            }
            if (json.lastIndexOf("]") == json.length()) {
                json = json.substring(0, json.length() - 1);
            }
        }
        return new JSONArray(json);
    }

    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String TimeStamp2Date(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return dateFormater.get().format(new Date(timestamp));
    }

    /**
     * 以友好的方式显示距离
     *
     * @param distance 距离
     * @return 友好的显示内容
     */
    public static String friendly_Distance(double distance) {
        String dis = "";
        if (distance >= 1000) {
            if (distance / 1000 >= 1000) {
                dis = ">1000km";
            } else if (distance / 1000 >= 100) {
                dis = ">100km";
            } else if (distance / 1000 >= 10) {
                dis = ">10km";
            } else if (distance / 1000 >= 1) {
                dis = ">1km";
            } else {
                dis = String.format("%.1f", (distance / 1000)) + "km";
            }
        } else {
//            dis = "<1km";
            dis = String.format("%.1f", distance) + "m";
        }
        // Log.v("以友好的方式显示距离", distance + "   " + dis);
        return dis;
    }

    public static String pointDistance(double distance) {
        Log.e("两点间距离 ", distance + "");
        String dis = "0.0km";
        if (distance >= 1000) {
            dis = String.format("%.1f", (distance / 1000)) + "km";
        } else {
            dis = String.format("%.1f", distance) + "m";
        }
        return dis;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate 日期
     * @return 显示内容
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate 时间
     * @return boolean 是否进日
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input 字符串
     * @return boolean 是否为空
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email 字符串
     * @return 是否符合 email 规则
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

}