package com.docker.apps.order.ui.logistics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.docker.apps.R;
import com.docker.apps.order.vo.LogisticeVo;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.MyViewHolder> {
    private Context mContext;
    private List<LogisticeVo.DataBean> mDataList;

    public TimerAdapter(Context context, List<LogisticeVo.DataBean> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pro_order_item_logistics, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LogisticeVo.DataBean bean = mDataList.get(position);
        if (null == bean) {
            return;
        }
        holder.tv_time.setText(bean.getFtime());
        holder.tv_desc.setText(bean.getContext());
//        if (bean.isCheck()) {
//            //设置圆点背景
//            holder.tv_time.setTextColor(Color.parseColor("#FFE60012"));
//            holder.tv_desc.setTextColor(Color.parseColor("#FFE60012"));
//            holder.tv_dot.setBackgroundResource(R.mipmap.ddgz_true);
//        } else {
//            //设置圆点背景
//            holder.tv_dot.setBackgroundResource(R.mipmap.ddgz_false);
//        }


        //处理顶部条目
        if (position == 0) {
            holder.tv_time.setTextColor(Color.parseColor("#FFE60012"));
            holder.tv_desc.setTextColor(Color.parseColor("#FFE60012"));
            holder.tv_dot.setBackgroundResource(R.mipmap.ddgz_true);
            holder.tv_line_top.setVisibility(View.INVISIBLE);
        } else if (position == mDataList.size() - 1) {
            //底部数据
            holder.tv_line_bottom.setVisibility(View.INVISIBLE);
            holder.view.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_time;
        private final TextView tv_desc;
        private final TextView tv_line_top;
        private final TextView tv_line_bottom;
        private final TextView tv_dot;
        private final TextView view;

        public MyViewHolder(View itemView) {
            super(itemView);

            //时间
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            //描述
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            //顶部线
            tv_line_top = (TextView) itemView.findViewById(R.id.tv_line_top);
            //圆点
            tv_dot = (TextView) itemView.findViewById(R.id.tv_dot);
            //底部线
            tv_line_bottom = (TextView) itemView.findViewById(R.id.tv_line_bottom);

            view = itemView.findViewById(R.id.view);


        }
    }
}
