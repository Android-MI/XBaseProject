package com.mi.xbaseprojectdemo;

import android.os.Bundle;

import com.mi.utils.CCLog;
import com.mi.xbaseproject.BaseActivity;

public class MainActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        CCLog.e("Log Message");
    }

    @Override
    protected void resetLayout() {
    }
}
