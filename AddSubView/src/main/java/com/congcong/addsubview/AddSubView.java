package com.congcong.addsubview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//自定义增加删除按钮
public class AddSubView extends LinearLayout implements View.OnClickListener {
    private final Context mContext;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;
    private int value = 1;
    private int minvalue = 1;
    private int maxvalue = 5;
    public AddSubView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.mContext = context;
        //把布局文件实例化，并且加载到AddSubView中
        View.inflate(context,R.layout.add_sub_view,this);
        iv_sub = findViewById(R.id.iv_sub);
        iv_add = findViewById(R.id.iv_add);
        tv_value = findViewById(R.id.tv_value);

        int value = getValue();
        setValue(value);
        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sub://减
                SubNumber();
                break;
            case R.id.iv_add://加
                AddNumber();
                break;
        }
       // Toast.makeText(mContext, "当前商品数"+value, Toast.LENGTH_SHORT).show();
    }

    private void AddNumber() {
        if (value <maxvalue){
            value ++;
        }
        setValue(value);
        if (onNumberChangeListener !=null){
            onNumberChangeListener.onNumberChange(value);
        }
    }
//减
    private void SubNumber() {
        if (value > minvalue){
            value --;
        }
            setValue(value);
        if (onNumberChangeListener !=null){
            onNumberChangeListener.onNumberChange(value);
        }
    }

    public int getValue() {
        String valuestr = tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(valuestr)){
            value = Integer.parseInt(valuestr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(int minvalue) {
        this.minvalue = minvalue;
    }

    public int getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }
    //当数量发生变化的时候回调
    public interface OnNumberChangeListener{
        public void onNumberChange(int value);
    }
    private OnNumberChangeListener onNumberChangeListener;
//设置量变化的监听
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
