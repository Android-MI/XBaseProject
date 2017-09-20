package com.mi.xbaseproject.common.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;


public class ApiAccess {
    private static ApiAccess apiAccess;
    private static ProgressDialog mProgressDialog;
    private static CustomProgressDialog mCustomProgressDialog;

    public static ApiAccess getInstance(Context context) {
        if (apiAccess == null) {
            apiAccess = new ApiAccess();
        }
        return apiAccess;
    }

    /**
     * 自定义ProgressDialog 样式
     *
     * @param activity 上下文
     * @param msg      要显示的文字信息
     * @param canBack  点击屏幕区域，是否让其自动消失<br/>
     *                 true ：可以 ； false ： 不可以
     * @Title showCustomProgress
     * @Description 自定义ProgressDialog 样式
     */
    public static void showCustomProgress(Activity activity, String msg,
                                          boolean canBack) {
        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.cancel();
        }
        mCustomProgressDialog = new CustomProgressDialog(activity, msg);
        mCustomProgressDialog.setCancelable(canBack);
        mCustomProgressDialog.setCanceledOnTouchOutside(canBack);
        mCustomProgressDialog.show();
    }

    public static void showCustomProgress(Activity activity, String msg) {
        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.cancel();
        }
        mCustomProgressDialog = new CustomProgressDialog(activity, msg);
        mCustomProgressDialog.setCancelable(false);
        mCustomProgressDialog.setCanceledOnTouchOutside(false);
        mCustomProgressDialog.show();
    }

    public static void dismissCustomProgressDialog() {
        if (mCustomProgressDialog == null) {
            return;
        }
        if (mCustomProgressDialog.isShowing()) {
            mCustomProgressDialog.dismiss();
        }
    }

    public static boolean isCustomProgressDialogShow(Activity activity) {
        if (mCustomProgressDialog == null) {
            return false;
        }
        return mCustomProgressDialog.isShowing();
    }

    @SuppressLint("InlinedApi")
    public static void showProgressDialog(Activity activity) {
        // mProgressDialog = new ProgressDialog(activity);
        // mProgressDialog.setCancelable(false);
        // mProgressDialog.setMessage(GlobalConstant.PROGRESS_DIALOG_MESSAGE);
        // mProgressDialog.show();

        mProgressDialog = new ProgressDialog(activity,
                ProgressDialog.THEME_HOLO_LIGHT);
        mProgressDialog.setMessage("加载中...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public static void showProgressDialog(Activity activity, String msg) {
        mProgressDialog = new ProgressDialog(activity,
                ProgressDialog.THEME_HOLO_LIGHT);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public static void showProgressDialog(Activity activity, String msg,
                                          int theme) {

        mProgressDialog = new ProgressDialog(activity, theme);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public static boolean isProgressDialogShow(Activity activity) {
        if (mProgressDialog == null) {
            return false;
        }
        return mProgressDialog.isShowing();
    }

    public static void dismissProgressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
