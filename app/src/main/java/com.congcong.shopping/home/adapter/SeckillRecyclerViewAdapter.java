package com.congcong.shopping.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.congcong.shopping.R;
import com.congcong.shopping.home.bean.ResultBeanData;
import com.congcong.shopping.utils.Constants;

import java.util.List;
//秒杀的适配器
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = View.inflate(mContext,R.layout.item_seckill,null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //1根据位置得到对应的数据
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);

        //2绑定数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+listBean.getFigure()).into(viewHolder.iv_figure);
        viewHolder.tv_cover_price.setText(listBean.getCover_price());
        viewHolder.tv_origin_price.setText(listBean.getOrigin_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_figure = itemView.findViewById(R.id.iv_figure);
            tv_cover_price = itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, "秒杀=="+getLayoutPosition(), Toast.LENGTH_SHORT).show();
                    if (onSeckillRecycler !=null){
                        onSeckillRecycler.OnItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }
    //监听器
    public interface OnSeckillRecycler{
        //当某条被=点击的时候被回调
        public void OnItemClick(int position);
    }
    private OnSeckillRecycler onSeckillRecycler;
        //设置item的监听
    public void setOnSeckillRecycler(OnSeckillRecycler onSeckillRecycler) {
        this.onSeckillRecycler = onSeckillRecycler;
    }
}
