package com.congcong.shopping.utils;

import android.content.Context;
import android.content.SharedPreferences;
//缓存工具类
public class CacheUtils {
    //得到保存的String的类型的数据
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
        //保存String类型数据
    //context 上下文
    public static void saveString(Context context, String key,String value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
}
