package com.docker.cirlev2.vo.entity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

import timber.log.Timber;

public class CircleListNomalVo extends CircleListVo {
    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("=========333================");

            ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_INDEX)
                    .withString("circleid", ((CircleListNomalVo) item).circleid)
                    .withString("utid", ((CircleListNomalVo) item).getUtid())
                    .withString("circletype", ((CircleListNomalVo) item).getType())
                    .navigation();
        };
    }

    @Override
    public int getItemLayout() {

        return R.layout.circlev2_item_nomal_circle;
    }

}
