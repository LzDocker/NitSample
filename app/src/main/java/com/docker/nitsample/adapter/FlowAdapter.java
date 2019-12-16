package com.docker.nitsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.vo.RstServerVo;
import com.docker.nitsample.R;

import java.util.List;


public class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RstServerVo> list;
    private Context context;
    private ReplyCommandParam replyCommandParam;

    public FlowAdapter(List<RstServerVo> list, Context context, ReplyCommandParam replyCommandParam) {
        this.list = list;
        this.context = context;
        this.replyCommandParam = replyCommandParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(context, R.layout.hot_item_search, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView textView = ((MyHolder) holder).text;
        textView.setText(list.get(position).name);
        holder.itemView.setOnClickListener(v -> replyCommandParam.exectue(list.get(position).name));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_hot);
        }
    }
}
