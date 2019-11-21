package com.docker.cirlev2.util;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ItemEditMemebrGroupBinding;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperAdapter;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.Collections;

public class CircleEditMemberGroupAdapter extends SimpleCommonRecyclerAdapter<MemberGroupingVo> implements ItemTouchHelperAdapter {


    public boolean editFlag = false;

    public CircleEditMemberGroupAdapter() {
        super(R.layout.circlev2_item_edit_memebr_group, BR.item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Circlev2ItemEditMemebrGroupBinding mbinding = (Circlev2ItemEditMemebrGroupBinding) holder.getBinding();
        mbinding.setEditFlag(editFlag);
        mbinding.circleTvDel.setOnClickListener(v -> {
            getmObjects().remove(position);
            CircleEditMemberGroupAdapter.this.notifyItemRemoved(position);
            CircleEditMemberGroupAdapter.this.notifyItemRangeChanged(position, getmObjects().size() - position);
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(this.getmObjects(), fromPosition, toPosition);
        this.notifyItemMoved(fromPosition, toPosition);
        this.notifyItemRangeChanged(0, this.getmObjects().size());
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
