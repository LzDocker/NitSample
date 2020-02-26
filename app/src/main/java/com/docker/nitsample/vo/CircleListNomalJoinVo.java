package com.docker.nitsample.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.vo.entity.CircleListNomalVo;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.nitsample.R;

import timber.log.Timber;

public class CircleListNomalJoinVo extends CircleListVo {


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

        return R.layout.app_item_circle_join_st2;
    }
}
