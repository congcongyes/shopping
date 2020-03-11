package com.congcong.shopping.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.congcong.shopping.R;
import com.congcong.shopping.home.bean.ResultBeanData;
import com.congcong.shopping.utils.Constants;

import java.util.List;

public class RecommendGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.RecommendInfoBean> datas;

    public RecommendGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext = mContext;
        this.datas = recommend_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertview ==null){
            convertview = View.inflate(mContext,R.layout.item_recommend_grid_view,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_recommend = convertview.findViewById(R.id.iv_recommend);
            viewHolder.tv_name = convertview.findViewById(R.id.tv_name);
            viewHolder.tv_price = convertview.findViewById(R.id.tv_price);
            convertview.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        //根据位置得到对应的数据
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+recommendInfoBean.getFigure()).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("￥"+recommendInfoBean.getCover_price());
        return convertview;
    }
    static class ViewHolder{
        ImageView iv_recommend;
        TextView tv_name;
        TextView tv_price;
    }
}
