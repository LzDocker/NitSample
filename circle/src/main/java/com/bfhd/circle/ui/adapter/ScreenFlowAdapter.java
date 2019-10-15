package com.bfhd.circle.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bfhd.circle.R;
import com.bfhd.circle.vo.HotItemVo;
import com.bfhd.circle.vo.ScreenVo2;
import com.docker.common.common.command.ReplyCommandParam;

import java.util.List;

import static com.dcbfhd.utilcode.utils.ConvertUtils.dp2px;

public class ScreenFlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ScreenVo2> list;
    private Context context;
    private ReplyCommandParam replyCommandParam;

    public ScreenFlowAdapter(List<ScreenVo2> list, Context context, ReplyCommandParam replyCommandParam) {
        this.list = list;
        this.context = context;
        this.replyCommandParam = replyCommandParam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(context, R.layout.circle_pop_goods_select_item_gtoup, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView textView = ((MyHolder) holder).text;
        textView.setText(list.get(position).getName());
        String type = list.get(position).getType();
//        holder.itemView.setOnClickListener(v -> replyCommandParam.exectue(list.get(position).name));

        RecyclerView recyclerView = ((MyHolder) holder).recyclerView;
        List<HotItemVo> itemData = list.get(position).getCheck_val();

//        List<List<String>> itemData = list.get(position).getCheck_val();
//        List<HotItemVo> hotItemVoList = new ArrayList<>();
//        for (int i = 0; i < itemData.size(); i++) {
//            List<String> data = itemData.get(i);
//            HotItemVo hotItemVo = new HotItemVo(data.get(1), data.get(0));
//            hotItemVoList.add(hotItemVo);
//        }

//        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
//        recyclerView.setLayoutManager(flowLayoutManager);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
//        ScreenFlowAdapter2 screenFlowAdapter = new ScreenFlowAdapter2(itemData,type, context, replyCommandParam);
//        recyclerView.setAdapter(screenFlowAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private RecyclerView recyclerView;

        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_itemContent);
            recyclerView = itemView.findViewById(R.id.rv_screen);
        }
    }
}
