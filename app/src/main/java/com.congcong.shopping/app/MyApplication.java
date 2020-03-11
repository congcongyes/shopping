package com.congcong.shopping.app;

import android.app.Application;
import android.content.Context;
import android.nfc.Tag;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;

//整个软件
public class MyApplication extends Application {
    public static Context getContext() {
        return mContext;
    }

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化OkhttpUtils
        initOkhttpClient();


    }

    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
