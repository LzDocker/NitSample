package com.docker.apps.active.vo.card;

import android.view.View;

import com.docker.apps.R;
import com.docker.common.common.vo.card.BaseCardVo;

public class ActiveDetailHeadCard extends BaseCardVo {


    public ActiveDetailHeadCard(int style, int position) {
        super(style, position);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_active_head_card;
    }
}
