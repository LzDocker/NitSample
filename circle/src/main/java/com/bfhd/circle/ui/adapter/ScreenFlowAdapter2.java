package com.bfhd.circle.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfhd.circle.R;
import com.bfhd.circle.vo.HotItemVo;
import com.bfhd.circle.vo.ScreenVo;
import com.docker.common.common.command.ReplyCommandParam;

import java.util.List;

public class ScreenFlowAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HotItemVo> list;
    private Context context;
    private String type;
    private ReplyCommandParam replyCommandParam;

    public ScreenFlowAdapter2(List<HotItemVo> list, String type, Context context, ReplyCommandParam replyCommandParam) {
        this.list = list;
        this.type = type;
        this.context = context;
        this.replyCommandParam = replyCommandParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(context, R.layout.circle_screen_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView textView = ((MyHolder) holder).text;
        HotItemVo hotItemVo = list.get(position);

        textView.setText(hotItemVo.getName());

        if (hotItemVo.isCheck() == true) {
            textView.setTextColor(Color.parseColor("#E60012"));
        } else {
            textView.setTextColor(Color.parseColor("#000000"));
        }

        textView.setOnClickListener(v -> {
            if ("1".equals(type)) {//单选
//                for (int i = 0; i < list.size(); i++) {
//                    list.get(i).setCheck(false);
//                }
//                hotItemVo.setCheck(true);
//                textView.setTextColor(Color.parseColor("#E60012"));

                    for (int i = 0; i < list.size(); i++) {
                        if (i == position){
                            if (hotItemVo.isCheck() == true){
                                hotItemVo.setCheck(false);
                                textView.setTextColor(Color.parseColor("#000000"));
                            }else {
                                hotItemVo.setCheck(true);
                                textView.setTextColor(Color.parseColor("#E60012"));
                            }
                        }else {
                            list.get(i).setCheck(false);
                        }
                    }
                notifyDataSetChanged();
            } else if ("2".equals(type)) {//多选
                if (hotItemVo.isCheck() == true) {
                    hotItemVo.setCheck(false);
                    textView.setTextColor(Color.parseColor("#000000"));
                } else {
                    hotItemVo.setCheck(true);
                    textView.setTextColor(Color.parseColor("#E60012"));
                }
            }

//            notifyDataSetChanged();
        });

//        textView.setOnClickListener(v -> replyCommandParam.exectue(
//                list.get(position).getId())
//        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_select_circle_list_title);
        }
    }
}
