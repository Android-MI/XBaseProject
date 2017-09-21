package com.mi.xbaseproject.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mi.xbaseproject.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类
 */
public class Utils {

    private static long lastClickTime;

    public static void canShowComplete(final Context context,
                                       final TextView textView) {
        if (textView == null) {
            return;
        }

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                TextPaint paint = textView.getPaint();
                float textWidth = Layout.getDesiredWidth(textView.getText(),
                        paint);
                int textViewWidth = textView.getMeasuredWidth();

                if (textWidth > textViewWidth) {
                    Toast toast = Toast.makeText(context, textView.getText(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources()
                        .getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public static boolean isViewEmpty(TextView view) {

        return isStringEmpty(strFromView(view));
    }

    public static String strFromView(View view) {
        String strText = "";
        if (null != view) {
            if ((view instanceof TextView)) {
                strText = ((TextView) view).getText().toString().trim();
            } else if (view instanceof EditText) {
                strText = ((EditText) view).getText().toString().trim();
            }
        }
        return strText;
    }

    public static boolean isEqual(String str1, String str2) {
        if (null == str2) {
            return false;
        }
        return str1.equals(str2);
    }

    public static boolean isViewEmpty(View view) {
        if (view == null) {
            return true;
        }
        String strText = "";
        if ((view instanceof TextView)) {
            strText = ((TextView) view).getText().toString().trim();
        } else if (view instanceof EditText) {
            strText = ((EditText) view).getText().toString().trim();
        }
        return isStringEmpty(strText);
    }

    public static boolean isStringEmpty(String str) {

        return null == str || "".equals(str);
    }


    public static boolean isArrayIndexOutOfBounds(String[] mCookieValues) {
        return mCookieValues.length > 1;
    }

    public static String formatDate(String strNoticeDate, String oldFormat,
                                    String newFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
            strNoticeDate = sdf2.format(sdf.parse(strNoticeDate));
        } catch (ParseException e) {
        }
        return strNoticeDate;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    public static boolean fileIsExists(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // use for camera
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }

    public static float getScreenRate(Context context) {
        Point P = getScreenMetrics(context);
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @SuppressLint("SimpleDateFormat")
    public static String getStringDate(Long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(date);

        return dateString;
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
    // public static boolean isMobileNum(String mobiles) {
    // Pattern p = Pattern
    // .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    // Matcher m = p.matcher(mobiles);
    // return m.matches();
    // }

    public static boolean isPersonCardValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        if (text.length() == 0) {
            return false;
        } else {
            return text.matches(regx) || text.matches(reg1)
                    || text.matches(regex);
        }

    }

    public static String resetMobile(String tel) {
        String result = "";
        if (Utils.isStringEmpty(tel)) {
            return tel;
        }
        if (tel.length() != 11) {
            return tel;
        }
        result = tel.substring(0, tel.length() - (tel.substring(3)).length())
                + "****" + tel.substring(7);

        return result;
    }

    @SuppressLint("NewApi")
    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission) ==
                    PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }


    public static void setMaxEcplise(final TextView mTextView,
                                     final int maxLines, final String content) {

        ViewTreeObserver observer = mTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView.setText(content);
                if (mTextView.getLineCount() > maxLines) {
                    int lineEndIndex = mTextView.getLayout().getLineEnd(
                            maxLines - 1);
                    // 下面这句代码中：我在项目中用数字3发现效果不好，改成1了
                    String text = content.subSequence(0, lineEndIndex - 5)
                            + "...";
                    mTextView.setText(text);
                } else {
                    removeGlobalOnLayoutListener(
                            mTextView.getViewTreeObserver(), this);
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static void removeGlobalOnLayoutListener(ViewTreeObserver obs,
                                                     ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (obs == null)
            return;
        if (Build.VERSION.SDK_INT < 16) {
            obs.removeGlobalOnLayoutListener(listener);
        } else {
            obs.removeOnGlobalLayoutListener(listener);
        }
    }

    public static int getStatusBarHeightPixel(Context context) {
        int result = 0;
        int resourceId =
                context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getActionBarHeightPixel(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        } else if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        } else {
            return 0;
        }
    }

    public static int getTabHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.tab_height);
    }

    public static Point getDisplayDimen(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        return size;
    }

    public static boolean isLand(Context context) {
        return context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
    }
}
