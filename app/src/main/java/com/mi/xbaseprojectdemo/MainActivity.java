package com.mi.xbaseprojectdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mi.utils.CCLog;
import com.mi.xbaseproject.BaseActivity;
import com.mi.xbaseproject.common.view.CropCircleTransformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.tv_text_sub_title)
    TextView mTvSubTitle ;
    @Nullable
    @BindView(R.id.iv_text_sub_title)
    ImageView mIvTest ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        CCLog.e("Log Message");
        mTvSubTitle.setText("HAHA");
        Glide.with(this).load("http://upload-images.jianshu.io/upload_images/926747-e90b02a83e8aa382.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240").bitmapTransform(new CropCircleTransformation(this)).into(mIvTest);
    }

    @Override
    protected void resetLayout() {
    }
}
