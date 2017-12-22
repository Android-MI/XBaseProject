package com.mi.xpdemo.fragment;

import android.content.Intent;
import android.view.View;

import com.mi.xpdemo.WebActivity;
import com.mi.xpdemo.model.ItemModel;

import java.util.List;

public class DemoFragment extends MainFragment {

    @Override
    public void fillData(List<ItemModel> items) {

        items.add(new ItemModel("Title -demo",
                "Desc -demo"));
        items.add(new ItemModel("Title -demo",
                "Desc -demo"));
        items.add(new ItemModel("Title -demo",
                "Desc -demo"));
        items.add(new ItemModel("Title -demo",
                "Desc -demo"));

    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) startActivity(new Intent(context, WebActivity.class));
//        WebActivity.runActivity(getActivity(), "BaiDu", "http://www.baidu.com");
    }

    @Override
    protected void resetLayout(View view) {

    }

    @Override
    protected void initLazyData() {
            super.initLazyData();
    }
}
