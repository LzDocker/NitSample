package com.docker.cirlev2.vo.entity;

import com.docker.cirlev2.R;
import com.docker.common.common.model.OnItemClickListener;

import timber.log.Timber;

public class CircleListNomalVo extends CircleListVo {
    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("=========================");
        };
    }

    @Override
    public int getItemLayout() {

        return R.layout.circlev2_item_nomal_circle;
    }

}
