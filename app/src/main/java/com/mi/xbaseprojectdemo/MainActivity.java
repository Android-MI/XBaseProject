package com.mi.xbaseprojectdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mi.utils.CCLog;
import com.mi.xbaseproject.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.tv_text_sub_title)
    TextView mTvSubTitle ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        CCLog.e("Log Message");
    }

    @Optional
    @OnClick(R.id.button_1)
    public void bindViewClick(){
        mTvSubTitle.setText("ButterKnife Bind Success !");
    }
    @Override
    protected void resetLayout() {
    }
}
