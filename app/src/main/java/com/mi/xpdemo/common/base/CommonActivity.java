package com.mi.xpdemo.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mi.xproject.BaseActivity;
import com.mi.xproject.common.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 共通Activity
 */
public abstract class CommonActivity extends BaseActivity {

    protected void parseOnSuccessResult(String response, String url) {
        String message = "";
        if (Utils.isStringEmpty(response)) {
            onApiFailure(message, url);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("message")) {
                message = jsonObject.optString("message");
            }
            if (jsonObject.has("success")) {//0错误 1成功
                if (jsonObject.optString("success").equals("true") || jsonObject.optBoolean("success", false)) {
                    onApiSuccess(jsonObject, message, url);
                } else {
                    onApiFailure(message, url);
                }
            }

        } catch (NullPointerException ex) {
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器返回数据信息解析- code非200的情况
     * @param message 提示信息
     */
    protected void onApiFailure(String message, String url) {
        showToast(message);
    }

    protected void onApiSuccess(JSONObject object, String successTip, String url) {
    }

    protected class BaseViewPagerAdapter extends FragmentPagerAdapter {

        String[] tabTitles;
        Fragment[] tabFragments;
        FragmentManager fm;

        public BaseViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        public BaseViewPagerAdapter(FragmentManager fm, String[] titles, Fragment[] fragments) {
            super(fm);
            this.tabTitles = titles;
            this.tabFragments = fragments;
            this.fm = fm;
        }

        @Override
        public Fragment getItem(final int position) {
            return tabFragments[position];
        }

        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }

}
