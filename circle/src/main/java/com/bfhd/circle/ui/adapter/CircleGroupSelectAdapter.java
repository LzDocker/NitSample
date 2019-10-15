package com.bfhd.circle.ui.adapter;

import android.view.Gravity;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.databinding.CircleItemGroupSelectBinding;
import com.bfhd.circle.databinding.CircleItemGroupTagsBinding;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import java.util.List;

//选择分类
public class CircleGroupSelectAdapter extends SimpleCommonRecyclerAdapter<CircleTitlesVo> {

    public CircleGroupSelectAdapter() {
        super(R.layout.circle_item_group_select, BR.item);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CircleItemGroupSelectBinding mbinding = (CircleItemGroupSelectBinding) holder.getBinding();
        mbinding.getRoot().setOnClickListener(v -> {
            for (int i = 0; i < getmObjects().size(); i++) {
                getmObjects().get(i).setClicked(false);
                if (getmObjects().get(i).getChildClass() != null && getmObjects().get(i).getChildClass().size() > 0) {
                    for (int j = 0; j < getmObjects().get(i).getChildClass().size(); j++) {
                        getmObjects().get(i).getChildClass().get(j).setClicked(false);
                    }
                }
            }
            getItem(position).setClicked(true);
            CircleGroupSelectAdapter.this.notifyDataSetChanged();
        });


        List<CircleTitlesVo> innerdata = getItem(position).getChildClass();


        HivsAbsSampleAdapter mInnerAdapter = new HivsAbsSampleAdapter(R.layout.circle_item_group_tags, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                CircleItemGroupTagsBinding tagsBinding = (CircleItemGroupTagsBinding) holder.getBinding();
                if (((CircleTitlesVo) getItem(position)).isClicked()) {
                    tagsBinding.tvItemContent.setBackgroundResource(R.drawable.circle_shape_round_pri);
                } else {
                    tagsBinding.tvItemContent.setBackgroundResource(R.drawable.circle_shape_round_gray);
                }
            }
        };
        ChipsLayoutManager hotLayoutManager = ChipsLayoutManager.newBuilder(mbinding.getRoot().getContext())
                .setChildGravity(Gravity.TOP)
                .setScrollingEnabled(false)
                .setMaxViewsInRow(100)
                .setGravityResolver(position1 -> Gravity.LEFT)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        mbinding.recycle.setLayoutManager(hotLayoutManager);
        mbinding.recycle.setNestedScrollingEnabled(false);
        mbinding.recycle.setAdapter(mInnerAdapter);
        mInnerAdapter.getmObjects().addAll(innerdata);
        mInnerAdapter.setOnItemClickListener((view, position12) -> {
            for (int i = 0; i < mInnerAdapter.getmObjects().size(); i++) {
                CircleTitlesVo bean = (CircleTitlesVo) mInnerAdapter.getmObjects().get(i);
                bean.setClicked(false);
            }
            CircleTitlesVo vo = (CircleTitlesVo) mInnerAdapter.getItem(position12);
            vo.setClicked(!vo.isClicked());
            mInnerAdapter.notifyDataSetChanged();
        });
    }
}
