package com.docker.cirlev2.util;

import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ItemEditClassBinding;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.BR;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperAdapter;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import java.util.Collections;

public class CircleEditClassAdapter extends SimpleCommonRecyclerAdapter<CircleTitlesVo> implements ItemTouchHelperAdapter {


    public boolean editFlag = false;

    public CircleEditClassAdapter() {
        super(R.layout.circlev2_item_edit_class, BR.item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Circlev2ItemEditClassBinding mbinding = (Circlev2ItemEditClassBinding) holder.getBinding();
        mbinding.setEditFlag(editFlag);
        mbinding.circleTvDel.setOnClickListener(v -> {
            getmObjects().remove(position);
            CircleEditClassAdapter.this.notifyItemRemoved(position);
            CircleEditClassAdapter.this.notifyItemRangeChanged(position,getmObjects().size()-position);
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
