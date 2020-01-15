package com.docker.cirlev2.vo.entity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

public class ServerGoodsDataBean extends ServiceDataBean {


    @Override
    public int getItemLayout() {
        int lay = R.layout.circlev2_item_dynamic_goods_banner;
        switch (this.getType()) {
            case "goods":
                lay = R.layout.circlev2_item_dynamic_goods_banner;
                break;
        }
        return lay;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", ((ServerGoodsDataBean) item).getDynamicid()).navigation();
    }
}
