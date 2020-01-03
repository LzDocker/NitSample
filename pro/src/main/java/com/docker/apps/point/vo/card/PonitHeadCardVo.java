package com.docker.apps.point.vo.card;

import android.view.View;

import com.docker.apps.R;
import com.docker.common.common.vo.card.BaseCardVo;

public class PonitHeadCardVo extends BaseCardVo {


    public PonitHeadCardVo(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_point_sort_head_card;
    }
}
