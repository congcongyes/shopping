package com.congcong.shopping.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.congcong.shopping.R;
import com.congcong.shopping.base.BaseFragment;
import com.congcong.shopping.home.adapter.HomeFragmentAdapter;
import com.congcong.shopping.home.bean.ResultBeanData;
import com.congcong.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

//主页面的Fragment
public class HomeFragment extends BaseFragment {
    private HomeFragmentAdapter adapter;

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    //返回的数据
    private ResultBeanData.ResultBean resultBean;

    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home,null);
        rvHome =  view.findViewById(R.id.rv_home);
        ib_top =  view.findViewById(R.id.ib_top);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        //设置点击事件
        initListener();
        return view;
    }
    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "主页数据被初始化了");
        //联网请求主页的数据
        initDataFromNet();
    }

    private void initDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    //请求失败的时候回调
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"首页请求失败=="+e.getMessage());

                    }
                    //请求成功的时候回调  response请求成功的数据
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"首页请求成功=="+response);
                        //解析数据
                        ProcessData(response);
                    }

                });
    }

    private void ProcessData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        if (resultBean != null){
            //有数据
            //设置适配器
            adapter = new HomeFragmentAdapter(mContext,resultBean);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mContext,1);
            //设置跨度大小的监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <=4){
                        //隐藏
                        ib_top.setVisibility(View.GONE);
                    }else {
                        //显示
                        ib_top.setVisibility(View.VISIBLE);

                    }
                    return 1;
                }
            });
            //设置布局管理者
            rvHome.setLayoutManager(manager);
        }else {
            //没有数据

        }
        Log.e(TAG,"解析成功=="+resultBean.getHot_info().get(0).getName());
    }

    private void initListener() {
        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                rvHome.scrollToPosition(0);
            }
        });
        //搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
