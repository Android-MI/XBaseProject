package com.mi.xbaseprojectdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.mi.utils.CCLog;
import com.mi.xbaseproject.BaseActivity;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        CCLog.e("Log Message");
    }

    @Override
    protected void resetLayout() {
        TextView textView = findView(R.id.tv_text_sub_title);
        textView.setText("NEW TITLE");
    }
}
