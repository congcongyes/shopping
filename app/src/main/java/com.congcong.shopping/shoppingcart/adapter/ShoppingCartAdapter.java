package com.congcong.shopping.shoppingcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.congcong.shopping.R;
import com.congcong.shopping.home.bean.GoodsBean;
import com.congcong.shopping.shoppingcart.utils.CartStorage;
import com.congcong.shopping.shoppingcart.view.AddSubView;
import com.congcong.shopping.utils.Constants;

import java.util.List;

//适配器的构造方法
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private final Context mContext;
    private final List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    //完成状态下的删除CheckBox
    private final CheckBox cbAll;


    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        this.mContext = mContext;
        this.datas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1根据位置找到对应的Bean对象
                GoodsBean goodsBean = datas.get(position);
                //2设置取反状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //3刷新状态
                notifyItemChanged(position);
                //4校验是否全选
                checkAll();
                //5重新计算总价格
                showTotalPrice();
            }
        });
        //设置checkbox的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1得到状态
                boolean isCheck = checkboxAll.isChecked();

                //2根据状态设置全选和非全选
                checkAll_none(isCheck);

                //3计算总价格
                showTotalPrice();
            }
        });
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1得到状态
                boolean isCheck = cbAll.isChecked();

                //2根据状态设置全选和非全选
                checkAll_none(isCheck);

                //3计算总价格
                //showTotalPrice();
            }
        });
    }
//设置全选和非全选
    public void checkAll_none(boolean isCheck) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i=0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isSelected()){
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                }else {
                    //选中的
                    number++;
                }
            }
            if (number == datas.size()){
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        }else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("合计:"+getTotalPrice());
    }
        //计算总价格
    private double getTotalPrice() {
        double totalprice = 0.0;
        if (datas !=null && datas.size() > 0){
            for (int i =0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()){
                    totalprice = totalprice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalprice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = View.inflate(mContext, R.layout.item_shop_cart,null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //1根据位置得到对应的Bean对象
        final GoodsBean goodsBean = datas.get(position);
        //2设置数据
        holder.cb_gov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("￥"+goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMinvalue(1);
        holder.addSubView.setMaxvalue(9);


        //设置商品数量的变化
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1当前列表内存中
                goodsBean.setNumber(value);
                // 2本地要更新
                CartStorage.getInstance().updataData(goodsBean);
                //3刷新适配器
                notifyItemChanged(position);
                //4再次计算总价格
                showTotalPrice();

            }
        });



    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void deleteData() {
        if (datas !=null && datas.size()>0){
            for (int i=0;i<datas.size();i++){
                //删除选中的
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()){
                    //内存中移除
                    datas.remove(goodsBean);
                    //保存到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemChanged(i);
                    i--;

                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView addSubView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_gov = itemView.findViewById(R.id.cb_gov);
            iv_gov = itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = itemView.findViewById(R.id.tv_price_gov);
            addSubView = itemView.findViewById(R.id.addSubView);
            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener !=null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
    }
    }
    //点击item的监听者
    public interface OnItemClickListener{
        //当点击某一条的时候被回调
        public  void  onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;
            //设置item的监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
