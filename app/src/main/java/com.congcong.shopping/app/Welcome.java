package com.congcong.shopping.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.congcong.shopping.R;

//启动页面

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //两秒钟进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行在主线程
                startActivity(new Intent(Welcome.this, MainActivity.class));
                //关闭当前页面
                finish();
//启动主页面
            }
        },2000);
    }
}
