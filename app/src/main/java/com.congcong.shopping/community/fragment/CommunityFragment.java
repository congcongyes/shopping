package com.congcong.shopping.community.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.congcong.shopping.base.BaseFragment;
//发现页面Fragment
public class CommunityFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("我是发现页面");
    }
}
