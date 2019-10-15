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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CirclePersionListAdapter extends SectionedRecyclerViewAdapter<SectionBaseViewHolder, SectionBaseViewHolder, SectionBaseViewHolder> {


    List<TradingCommonVo> sectionOuter = new ArrayList<>();
    private Context context;
    ArrayList<Integer> darbles = new ArrayList<Integer>();


    public CirclePersionListAdapter(Context context) {
        this.context = context;
        darbles.add(R.drawable.circle_ov_shape);
        darbles.add(R.drawable.circle_ov_shape1);
        darbles.add(R.drawable.circle_ov_shape3);
        darbles.add(R.drawable.circle_ov_shape4);
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
//            if (headerBinding.getItem().getEmployee() == null || headerBinding.getItem().getEmployee().size() == 0) {
//                return;
//            }
//            headerBinding.getItem().isExpand = !headerBinding.getItem().isExpand;
//            notifyDataSetChanged();
//        });
//        if (headerBinding.getItem().isExpand) {
////            Glide.with(headerBinding.getRoot().getContext()).load(R.mipmap.arrow_right).into(headerBinding.ivOutArrow);
//        } else {
//            Glide.with(headerBinding.getRoot().getContext()).load(R.mipmap.circle_arrow_down_icon).into(headerBinding.ivOutArrow);
//        }
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
        Random random = new Random();
        int i = random.nextInt(darbles.size());
        itembinding.roundIcon.setBackgroundDrawable(context.getResources().getDrawable(darbles.get(i)));
    }

    public interface OnItemCilckListener {
        void onItemClick(int section, int position);
    }

    private OnItemCilckListener onItemCilckListener;

    public void setOnItemCilckListener(OnItemCilckListener onItemCilckListener) {
        this.onItemCilckListener = onItemCilckListener;
    }
}
