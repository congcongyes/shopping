package com.congcong.shopping.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.congcong.shopping.app.MyApplication;
import com.congcong.shopping.home.bean.GoodsBean;
import com.congcong.shopping.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private  static  CartStorage instance;
    private final Context mContext;
    private SparseArray<GoodsBean>sparseArray;

    private CartStorage(Context context){
        this.mContext = context;
        //把之前存储的数据读取
        //SparseArray的性能优于HashMAP
        sparseArray = new SparseArray<>(100);

        listtoSparseArray();
    }
        //从本地读取的数据加入SparseArray中
    private void listtoSparseArray() {

        List<GoodsBean>goodsBeanList = getAllData();
        //把List数据转换成SparseArray数据
        for (int i=0; i<goodsBeanList.size();i++){
            GoodsBean goodsBean = goodsBeanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }

    }
            //获取本地所有的数据
    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //1从本地获取
        String json = CacheUtils.getString(mContext, JSON_CART);
        //2使用Gson转换成列表
        //判断是否为空
        if (!TextUtils.isEmpty(json)){
            //把String转换成List

            List<GoodsBean> goodsBean = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>(){}.getType()
            ); // 参数二：需要指定类型，类型来决定解析的集合

            goodsBeanList.addAll(goodsBean);
        }
        return goodsBeanList;
    }

    //得到购物车实例
    public static CartStorage getInstance(){
        if (instance ==null){
            instance = new CartStorage(MyApplication.getContext());
        }
        return instance;

    }
    //添加数据
    public void addData(GoodsBean goodsBean){

        //1添加到内存中SparseArray
        //如果当前数据已经存在,就修改成number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData !=null){
            //内存中有了这条数据
            tempData.setNumber(tempData.getNumber()+1);

        }else {
            tempData = goodsBean;
            tempData.setNumber(1);
        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);

        //2同步到本地
        saveLocal();
    }
    //删除数据
    public void deleteData(GoodsBean goodsBean){
        //1要在内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        //2把内存的保存到本地
        saveLocal();

    }
    //更新数据
    public void updataData(GoodsBean goodsBean){
        //1内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        //2同步到本地
        saveLocal();

    }
//保存数据到本地
    private void saveLocal() {
        //1SparseArray转换成List
            List<GoodsBean> goodsBeanList = sparseToList();
        //2使用Gson把列表转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        //3把String数据类型保存
        CacheUtils.saveString(mContext,JSON_CART,json);

    }

    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i=0;i<sparseArray.size();i++){
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }
}
