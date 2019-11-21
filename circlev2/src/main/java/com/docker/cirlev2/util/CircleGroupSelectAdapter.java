package com.docker.cirlev2.util;

import android.view.Gravity;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ItemGroupSelectBinding;
import com.docker.cirlev2.databinding.Circlev2ItemGroupTagsBinding;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.List;

//选择分类
public class CircleGroupSelectAdapter extends SimpleCommonRecyclerAdapter<CircleTitlesVo> {

    public CircleGroupSelectAdapter() {
        super(R.layout.circlev2_item_group_select, BR.item);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Circlev2ItemGroupSelectBinding mbinding = (Circlev2ItemGroupSelectBinding) holder.getBinding();
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


        NitAbsSampleAdapter mInnerAdapter = new NitAbsSampleAdapter(R.layout.circlev2_item_group_tags, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                Circlev2ItemGroupTagsBinding tagsBinding = (Circlev2ItemGroupTagsBinding) holder.getBinding();
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
