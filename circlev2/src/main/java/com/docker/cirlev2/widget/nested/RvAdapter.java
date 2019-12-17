package com.docker.cirlev2.widget.nested;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.docker.cirlev2.R;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter {
    private List<InfoBean> mData;
    private LayoutInflater mInflater;

    public void setmData(List<InfoBean> mData){
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public RvAdapter(Context context, List<InfoBean> datas) {
        this.mData = datas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.d("sss", "onDetachedFromRecyclerView: ================");
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d("sss", "onViewAttachedToWindow: ==================");
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("sss", "onViewDetachedFromWindow: ==================");
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        Log.d("sss", "onViewRecycled: ==================");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case InfoBean.TYPE_ITEM:
                return new ContentHolder(mInflater.inflate(R.layout.layout_content_layout, viewGroup, false));
            case InfoBean.TYPE_TITLE:
                return new TitleHolder(mInflater.inflate(R.layout.layout_title_layout, viewGroup, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        InfoBean infoBean = mData.get(i);
        int viewType = getItemViewType(i);

        switch (viewType) {
            case InfoBean.TYPE_ITEM:
                ContentHolder contentHolder = (ContentHolder) viewHolder;
                contentHolder.tvTitle.setText(infoBean.title);
                contentHolder.tvContent.setText(infoBean.content);
                break;
            case InfoBean.TYPE_TITLE:
                TitleHolder titleHolder = (TitleHolder) viewHolder;
                titleHolder.tvTitle.setText(infoBean.title);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
    }

    public static class TitleHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        TitleHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;

        ContentHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
