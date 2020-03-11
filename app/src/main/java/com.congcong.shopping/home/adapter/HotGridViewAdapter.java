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

public class HotGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.HotInfoBean> datas;

    public HotGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.mContext = mContext;
        this.datas = hot_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertview ==null){
            convertview = View.inflate(mContext, R.layout.item_hot_grid_view,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_hot = convertview.findViewById(R.id.iv_hot);
            viewHolder.tv_name = convertview.findViewById(R.id.tv_name);
            viewHolder.tv_price = convertview.findViewById(R.id.tv_price);
            convertview.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        //根据位置得到对应的数据
        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+hotInfoBean.getFigure()).into(viewHolder.iv_hot);
        viewHolder.tv_name.setText(hotInfoBean.getName());
        viewHolder.tv_price.setText("￥"+hotInfoBean.getCover_price());
        return convertview;
    }
    class ViewHolder{
        ImageView iv_hot;
        TextView tv_name;
        TextView tv_price;

    }
}
