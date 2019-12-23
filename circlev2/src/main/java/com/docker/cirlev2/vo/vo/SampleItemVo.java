package com.docker.cirlev2.vo.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

public class SampleItemVo extends BaseSampleItem {


    @Override
    public int getItemLayout() {
        return R.layout.common_item_sample;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            switch (((SampleItemVo) item).flag) {
                case 0:
                    ARouter.getInstance().build(AppRouter.CIRCLE_INDEX).navigation();
                    break;
                case 1:
                    ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2_INDEX).navigation();
                    break;
            }
        };
    }

    public String title;
    public int flag;

    public SampleItemVo(String title, int flag) {
        this.title = title;
        this.flag = flag;
        this.sampleName = title;
    }
}
