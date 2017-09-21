package com.mi.xbaseproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.mi.xbaseproject.common.data.ILoginDataManager;
import com.mi.xbaseproject.common.data.LoginDataManagerSPImpl;
import com.mi.xbaseproject.common.utils.SystemBarTintManager;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Activity共通基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    public boolean isLandscape = false;
    public OnMDialogClickListener mOnMdialogPositiveClickListener = null;
    // 共通类
    protected BaseActivity mBaseActivity = null;
    protected BaseApplication mApplication = null;

    // 退出
    protected boolean mIsCancelAction = false;
    protected boolean mIsLogoutAction = false;

    // 对话框，提示语
    Toast mToast = null;
    MaterialDialog mMaterialDialog;
    private long waitTime = 2000;
    private long touchTime = 0;

    // 进度条
    private ProgressDialog dialog;

    public ILoginDataManager mLoginDataManager = LoginDataManagerSPImpl
            .getInstace(this);

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        mApplication = (BaseApplication) this.getApplication();
        mApplication.add(this);
        mBaseActivity = this;

        initSystemBarTint();
        SupportDisplay.initLayoutSetParams(BaseActivity.this);

        initBaseActivityConfig();
    }

    protected void initBaseActivityConfig() {
    }

    ;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        isLandscape = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        SupportDisplay.initLayoutSetParams(BaseActivity.this);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        isLandscape = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        SupportDisplay.initLayoutSetParams(BaseActivity.this);
        super.onResume();
    }

    protected abstract void resetLayout();

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        resetLayout();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        resetLayout();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        resetLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void baseStartActivity(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        baseStartActivity(intent);
    }

    public void baseStartActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);

    }

    protected void baseAddFragment(int container, Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (fragment != null) {
            if (fragment.isAdded()) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.replace(container, fragment);
            }
        }
        fragmentTransaction.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onKeyCodeBackListener();
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            onSearchButtonListener();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    private void onKeyCodeBackListener() {
        if (mIsCancelAction) {
            onCancelButtonListener();

        } else if (mIsLogoutAction) {

            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(this, "请再按一次退出程序!!", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                onLogoutAppAction();
                mApplication.finishAll();
            }

        } else {// 其他情况，默认关闭页面
            finish();
        }

    }

    protected void onLogoutAppAction() {

    }

    /**
     * android系统返回键按下后，各页面独自处理方法
     */
    protected void onCancelButtonListener() {
    }

    protected void onSearchButtonListener() {
    }

    public String getStringExtra(String key) {
        String result = "";
        if (getIntent().getStringExtra(key) != null) {
            result = getIntent().getStringExtra(key);
        }
        return result;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    public void callTelPhone(String telNo) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
            }

            return;
        }

        if (telNo != null && !telNo.equals("")) {
            String telphone = "tel:" + telNo;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(telphone));
            startActivity(intent);
        }
    }

    public android.support.v7.app.AlertDialog telDialog(String message, final String telNo) {

        return showDialog(mBaseActivity, null, message, null, "确定", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callTelPhone(telNo);
            }
        }, null, true);
    }

    public void showToast(final String info) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null && !isFinishing()) {
                    mToast.setText(info);
                    mToast.show();
                    return;
                }
                mToast = Toast.makeText(getApplicationContext(), info,
                        Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    // ************　ToolBar 相关　start *************** //

    /**
     * 子类可以重写改变状态栏颜色
     * @return statusBarColor
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     * @return status
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取深主题色
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    /**
     * 设置Toolbar 为ActionBar
     *
     * @param toolbarId Toolbar资源ID
     * @param showTitle 是否显示标题
     */
    public void setSupportActionBar(@IdRes int toolbarId, boolean showTitle) {
        Toolbar mToolbar = (Toolbar) findViewById(toolbarId);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new NavigationOnClickListener());
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
            }
        }
    }

    /**
     * 设置Toolbar 为ActionBar
     *
     * @param toolbarId Toolbar资源ID
     */
    public void setSupportActionBar(@IdRes int toolbarId) {
        setSupportActionBar(toolbarId, false);
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }

    /**
     * Toolbar 返回按钮点击
     */
    protected void onNavigationClick() {
        onBackPressed();
    }

    // ************　对话框相关　start *************** //
    private android.support.v7.app.AlertDialog showDialog(Context context, String title, String message, View contentView,
                                                          String positiveBtnText, String negativeBtnText,
                                                          DialogInterface.OnClickListener positiveCallback,
                                                          DialogInterface.OnClickListener negativeCallback,
                                                          boolean cancelable) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context,
                R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title == null ? "提示" : title);
        if (message != null) {
            builder.setMessage(message);
        }

        if (contentView != null) {
            builder.setView(contentView);
        }

        if (positiveBtnText != null) {
            builder.setPositiveButton(positiveBtnText, positiveCallback);
        }

        if (negativeBtnText != null) {
            builder.setNegativeButton(negativeBtnText, negativeCallback);
        }

        builder.setCancelable(cancelable);
        return builder.show();
    }

    // ************　ToolBar相关　end *************** //

    //普通对话框
    public android.support.v7.app.AlertDialog showSimpleDialog(Context context, String title, String message,
                                                               String positiveBtnText, String negativeBtnText,
                                                               DialogInterface.OnClickListener positiveCallback,
                                                               DialogInterface.OnClickListener negativeCallback,
                                                               boolean cancelable) {

        return showDialog(context, title, message, null, positiveBtnText, negativeBtnText, positiveCallback, negativeCallback, cancelable);
    }

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("加载中...");
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showMDDialog(String title, String message) {
        mMaterialDialog = new MaterialDialog(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });

        mMaterialDialog.show();

    }

    public void showMDDialog(String title, String message, String positiveBtnText, String nevigationBtnText, final OnMDialogClickListener onMDialogPositiveClickListener) {
        mMaterialDialog = new MaterialDialog(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        if (onMDialogPositiveClickListener != null) {
                            onMDialogPositiveClickListener.onPositiveClick(v);
                        }
                    }
                })
                .setNegativeButton(nevigationBtnText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMDialogPositiveClickListener != null) {
                            onMDialogPositiveClickListener.onNegativeClick(v);
                        }
                    }
                });

        mMaterialDialog.show();
    }

    public void showMDDialog(String title, String message, final OnMDialogClickListener onMDialogPositiveClickListener) {
        mMaterialDialog = new MaterialDialog(this)
                .setTitle(title)
                .setCanceledOnTouchOutside(false)
                .setMessage(message)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        if (onMDialogPositiveClickListener != null) {
                            onMDialogPositiveClickListener.onPositiveClick(v);
                        }
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMDialogPositiveClickListener != null) {
                            onMDialogPositiveClickListener.onNegativeClick(v);
                        }
                    }
                });

        mMaterialDialog.show();
    }

    public void setmOnMdialogPositiveClickListener(OnMDialogClickListener mOnMdialogPositiveClickListener) {
        this.mOnMdialogPositiveClickListener = mOnMdialogPositiveClickListener;
    }

    public interface OnMDialogClickListener {

        void onPositiveClick(View v);

        void onNegativeClick(View v);
    }

    private class NavigationOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            onNavigationClick();
        }
    }

    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popwindowOnDismissListener implements
            android.widget.PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    // ************　对话框相关　end *************** //

//    public void addCommonAuthHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        if (mLoginDataManager != null && mLoginDataManager.getUserInfo() != null) {
//            UserModelBean userModelBean = UserModelBean.objectFromData(mLoginDataManager.getUserInfo());
//            if (userModelBean != null && userModelBean.data != null) {
//                CCLog.e("BaseActivity ApiToken = " + userModelBean.data.api_token);
//                headers.put("Authorization", "Bearer " + userModelBean.data.api_token);
//            }
//        } else {
//            headers.put("Authorization", "Bearer ");
//        }
//        OkGo.getInstance().addCommonHeaders(headers);
//    }


    /**
     * 用户退出登录后，清除信息
     */
    public void clearUserData() {
        mLoginDataManager.setLoginState(false);
        mLoginDataManager.deleteLoginUserInfo();
    }

    //通过反射获取状态栏高度，默认25dp
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = dip2px(context, 25);
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    //根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setToolbarPadding(Toolbar toolbar) {
        //设置透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            toolbar.setPadding(0, getStatusBarHeight(this), 0, 0); //给Toolbar设置paddingTop }
        }
    }


    public void setAlphaForView(View v, float alpha) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

//    public void actionBarResponsive() {
//        int actionBarHeight = Utils.getActionBarHeightPixel(this);
//        int tabHeight = Utils.getTabHeight(this);
//        if (actionBarHeight > 0) {
//            mTopToolBar.getLayoutParams().height = actionBarHeight + tabHeight;
//            mTopToolBar.requestLayout();
//        }
//    }

}
