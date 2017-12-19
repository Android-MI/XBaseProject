package com.mi.xpdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mi.utils.CCLog;
import com.mi.xpdemo.common.base.CommonActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends CommonActivity {

    @Nullable
    @BindView(com.mi.xpdemo.R.id.tv_text_sub_title)
    TextView mTvSubTitle ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int setLayoutId() {
        return com.mi.xpdemo.R.layout.activity_main;
    }

    private void init() {
        CCLog.e("Log Message");
    }

    @Optional
    @OnClick(com.mi.xpdemo.R.id.button_1)
    public void bindViewClick(){
        mTvSubTitle.setText("ButterKnife Bind Success !");
    }
    @Override
    protected void resetLayout() {
    }
}
