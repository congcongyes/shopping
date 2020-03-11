package com.congcong.shopping.user.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.congcong.shopping.base.BaseFragment;

//用户页面Fragment
public class UserFragment extends BaseFragment {
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
        textView.setText("我是用户页面");
    }
}
