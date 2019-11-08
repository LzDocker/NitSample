package com.docker.message.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.message.R;

import timber.log.Timber;

public class SampleItemVo extends BaseSampleItem {
    @Override
    public int getItemLayout() {
        return R.layout.common_item_sample;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("=========itemclick============");
            switch (((SampleItemVo) item).flag) {
                case 0:
                    Timber.e("=========itemclick========00000====");
                    ARouter.getInstance().build(AppRouter.MESSAGMAIN).withInt("style", 0).navigation();
                    break;
                case 1:
                    Timber.e("=========itemclick======1111======");
                    ARouter.getInstance().build(AppRouter.MESSAGMAIN).withInt("style", 1).navigation();
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
