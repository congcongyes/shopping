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

import org.w3c.dom.Text;

import java.util.List;

import butterknife.ButterKnife;
//频道的适配器

public class ChannelAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;

    public ChannelAdapter(Context mContext, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }

    @Override
    public int getCount() {
        return channel_info == null ? 0 :channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext,R.layout.item_channel,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        //根据位置得到对应的数据
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+channelInfoBean.getImage()).into(viewHolder.iv_icon);
        viewHolder.tv_title.setText(channelInfoBean.getChannel_name());
        return convertView;
    }
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
    }

}
