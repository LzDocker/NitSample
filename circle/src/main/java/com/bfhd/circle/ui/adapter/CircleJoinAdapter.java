package com.bfhd.circle.ui.adapter;


import android.view.View;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.databinding.CircleItemMycircleBinding;
import com.bfhd.circle.vo.CircleListVo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

public class CircleJoinAdapter extends SimpleCommonRecyclerAdapter<CircleListVo> {

    public CircleJoinAdapter() {
        super(R.layout.circle_item_mycircle, BR.item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CircleItemMycircleBinding mbinding = (CircleItemMycircleBinding) holder.getBinding();
        CircleListVo circleListVo = getItem(position);
        if (position == this.getmObjects().size() - 1) {
//            mbinding.ivThumb.setImageResource(R.mipmap.open_up_pic_icon);
            mbinding.tvTag1.setVisibility(View.INVISIBLE);
            mbinding.tvTag2.setVisibility(View.INVISIBLE);
            mbinding.tvName.setText("创建圈子");
        } else {
            Glide.with(holder.itemView.getContext()).load(Constant.getCompleteImageUrl(getItem(position).getLogoUrl())).apply(new RequestOptions().error(R.mipmap.circle_icon_empty).placeholder(R.mipmap.circle_icon_empty)).into(mbinding.ivThumb);
            mbinding.tvTag1.setVisibility(View.VISIBLE);
            mbinding.tvTag2.setVisibility(View.VISIBLE);
            mbinding.tvTag1.setText(circleListVo.classStr);
            mbinding.tvTag2.setText(circleListVo.classStr1);
        }

    }

}
