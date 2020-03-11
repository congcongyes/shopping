package com.congcong.shopping.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.congcong.shopping.R;
import com.congcong.shopping.base.BaseFragment;
import com.congcong.shopping.community.fragment.CommunityFragment;
import com.congcong.shopping.home.fragment.HomeFragment;
import com.congcong.shopping.shoppingcart.fragment.ShoppingCartFragment;
import com.congcong.shopping.type.fragment.TypeFragment;
import com.congcong.shopping.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends FragmentActivity {


    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    //装多个Fragment实例的集合
    private ArrayList<BaseFragment> fragments;
    private int position = 0;
    //缓存的或上次显示的Fragment
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Butterknife和当前Activity绑定
        ButterKnife.bind(this);
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        position = 0;
                        break;
                    case R.id.rb_type://分类
                        position = 1;
                        break;
                    case R.id.rb_community://发现
                        position = 2;
                        break;
                    case R.id.rb_cart://购物
                        position = 3;
                        break;
                    case R.id.rb_user://用户
                        position = 4;
                        break;
                        default:
                            position = 0;
                            break;
                }
                //根据位置取不同的Fragment
                BaseFragment baseFragment = getFragment(position);
                //第一参数:上次显示的Fragment
                //第二参数:当前正要显示的Fragment
                switchFragment(tempFragment,baseFragment);
            }
        });
        rgMain.check(R.id.rb_home);
    }
//添加的时候要按照顺序
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }
    //切换Fragment
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction =
                        getSupportFragmentManager().beginTransaction();
                //判断 nextFragment 是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前 Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.framelayout, nextFragment).commit();
                } else {
                    //隐藏当前 Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}
