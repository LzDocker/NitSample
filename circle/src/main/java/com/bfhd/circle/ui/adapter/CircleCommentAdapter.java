package com.bfhd.circle.ui.adapter;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.databinding.CircleItemEditClassBinding;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperAdapter;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.Collections;

public class CircleCommentAdapter extends SimpleCommonRecyclerAdapter<CircleTitlesVo.ChildBean> implements ItemTouchHelperAdapter {


    public boolean editFlag = false;

    public CircleCommentAdapter() {
        super(R.layout.circle_item_edit_class, BR.item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CircleItemEditClassBinding mbinding = (CircleItemEditClassBinding) holder.getBinding();
        mbinding.setEditFlag(editFlag);
        mbinding.circleTvDel.setOnClickListener(v -> {
            getmObjects().remove(position);
            CircleCommentAdapter.this.notifyItemRemoved(position);
            CircleCommentAdapter.this.notifyItemRangeChanged(position,getmObjects().size()-position);
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
