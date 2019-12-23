package com.docker.cirlev2.vo.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

import java.io.Serializable;

public class CircleCreateCardVo extends BaseSampleItem implements Serializable {

    public int icon;
    public String title;
    public String tag;
    public int flag;

    public CircleCreateCardVo(int icon, String title, String tag, int flag) {
        this.icon = icon;
        this.title = title;
        this.tag = tag;
        this.flag = flag;
    }


    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_create_circle;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2)
                    .withInt("flag", ((CircleCreateCardVo) item).flag)
//                    .withString("circleid", "245")
//                    .withString("utid", "98699115f2260ef14486f745fc72dbd1")
                    .navigation();
        };
    }
}
