package com.bfhd.circle.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bfhd.circle.R;
import com.bfhd.circle.base.adapter.SectionBaseViewHolder;
import com.bfhd.circle.base.adapter.SectionedRecyclerViewAdapter;
import com.bfhd.circle.databinding.CircleItemCirclePersionContentBinding;
import com.bfhd.circle.databinding.CircleItemCirclePersionHeaderBinding;
import com.bfhd.circle.vo.TradingCommonVo;
import java.util.ArrayList;
import java.util.List;

/*
*
* 评论列表
* */
public class CircleCommentListAdapter extends SectionedRecyclerViewAdapter<SectionBaseViewHolder, SectionBaseViewHolder, SectionBaseViewHolder> {


    List<TradingCommonVo> sectionOuter = new ArrayList<>();
    private Context context;

    public CircleCommentListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TradingCommonVo> sectionOuter) {
        this.sectionOuter = sectionOuter;
        notifyDataSetChanged();
    }

    public List<TradingCommonVo> getData() {
        return this.sectionOuter;
    }

    /*
     * 分组个数
     * */
    @Override
    protected int getSectionCount() {
        return sectionOuter.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = 0;
        if (sectionOuter.get(section).getEmployee() == null || sectionOuter.get(section).isExpand) {
            count = 0;
        } else {
            count = sectionOuter.get(section).getEmployee().size();
        }

        return count;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected SectionBaseViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.circle_item_circle_persion_header, parent, false);
        SectionBaseViewHolder viewHolder = new SectionBaseViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    protected SectionBaseViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected SectionBaseViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.circle_item_circle_persion_content, parent, false);
        SectionBaseViewHolder viewHolder = new SectionBaseViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(SectionBaseViewHolder holder, int section) {
//        CircleItemCirclePersionHeaderBinding headerBinding = (CircleItemCirclePersionHeaderBinding) holder.getBinding();
//        headerBinding.setItem(sectionOuter.get(section));
//        headerBinding.getRoot().setOnClickListener(v -> {
//            headerBinding.getItem().isExpand = !headerBinding.getItem().isExpand;
//            notifyDataSetChanged();
//        });
    }

    @Override
    protected void onBindSectionFooterViewHolder(SectionBaseViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(SectionBaseViewHolder holder, int section, int position) {
        CircleItemCirclePersionContentBinding itembinding = (CircleItemCirclePersionContentBinding) holder.getBinding();
        itembinding.setItem(sectionOuter.get(section).getEmployee().get(position));
        itembinding.getRoot().setOnClickListener(v -> {
            if (onItemCilckListener != null) {
                onItemCilckListener.onItemClick(section, position);
            }
        });
    }

    public interface OnItemCilckListener {
        void onItemClick(int section, int position);
    }

    private OnItemCilckListener onItemCilckListener;

    public void setOnItemCilckListener(OnItemCilckListener onItemCilckListener) {
        this.onItemCilckListener = onItemCilckListener;
    }
}
