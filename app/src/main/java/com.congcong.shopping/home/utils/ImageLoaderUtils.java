package com.congcong.shopping.home.utils;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
//banner加载图片
public class ImageLoaderUtils extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
    }
}
